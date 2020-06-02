package com.example.bookreview.viewModel

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bookreview.dto.BestSeller
import com.example.bookreview.dto.Response
import com.example.bookreview.repository.JsoupRepository
import com.example.bookreview.repository.KyoboRepository
import com.example.bookreview.repository.NaverOAuthRepository
import com.example.bookreview.repository.ServerRepository
import com.example.bookreview.utils.SingleLiveEvent
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.concurrent.TimeUnit

class MainViewModel(private val serverRepository: ServerRepository,
                    private val naverOAuthRepository: NaverOAuthRepository,
                    private val jsoupRepository: JsoupRepository,
                    private val preferences: SharedPreferences
) : ViewModel() {
    private val _isLoginFinished: SingleLiveEvent<Any> = SingleLiveEvent()
    val isLoginFinished: LiveData<Any>
        get() = _isLoginFinished
    private val _startLoadingIndicatorEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val startLoadingIndicatorEvent:LiveData<Any>
        get() = _startLoadingIndicatorEvent
    private val _stopLoadingIndicatorEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val stopLoadingIndicatorEvent:LiveData<Any>
        get() = _stopLoadingIndicatorEvent
    private val _isLoginFailed: SingleLiveEvent<Any> = SingleLiveEvent()
    val isLoginFailed:LiveData<Any>
        get() = _isLoginFailed
    private val _isLoadPopularListFinished: SingleLiveEvent<Any> = SingleLiveEvent()
    val isLoadPopularListFinished:LiveData<Any>
        get() = _isLoadPopularListFinished

    var userProfileImageSrc : String? = null
    var id : String? = null
    var popularBookList = ArrayList<BestSeller>()

    private val compositeDisposable = CompositeDisposable()
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun <T> apiCall(single: Single<T>, onSuccess: Consumer<in T>,
                    onError: Consumer<in Throwable> = Consumer {
                        Log.e("오류발생",it.message!!)
                    },
                    indicator : Boolean = false, timeout: Long = 5){
        addDisposable(single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(timeout, TimeUnit.SECONDS)
            .doOnSubscribe{ if(indicator) startLoadingIndicator()  }
            .doAfterTerminate { stopLoadingIndicator() }
            .subscribe(onSuccess, onError))
    }

    fun testLoad(){
        apiCall(serverRepository.requestResponse(),
        Consumer {
            Log.e("test",it.responseCode)
        })
    }


    fun testLogin(activity: Activity){
        startLoadingIndicator()
        if(preferences.getString("REFRESH_TOKEN",null) == null){
            apiCall(naverOAuthRepository.getLoginModule(activity),
                Consumer {
                    Log.e("REFRESH TOKEN 없음 : 재요청 결과 access token",it.accessToken)
                    Log.e("REFRESH TOKEN 없음 : 재요청 결과 refresh token",it.refreshToken)
                    testGetUserInfo(it.accessToken)
                },
            onError = Consumer {
                stopLoadingIndicator()
                Log.e("ERROR" , "ERROR : REFRESH ERROR")
            },
            indicator = false
            )
        }
        else{
            apiCall(naverOAuthRepository.refreshAccessToken(preferences.getString("REFRESH_TOKEN",null)!!),
                onSuccess = Consumer {
                    Log.e("refreshed access token",it.access_token)
                    preferences.edit().putString("ACCESS_TOKEN", it.access_token).apply()
                    testGetUserInfo(it.access_token)
            },
                onError = Consumer {
                    stopLoadingIndicator()
                    apiCall(naverOAuthRepository.getLoginModule(activity),
                        Consumer {
                            Log.e("refresh token 만료 : 결과 access token",it.accessToken)
                            Log.e("refresh token 만료 : 결과 refresh token",it.refreshToken)
                            testGetUserInfo(it.accessToken)
                        })
                },
                indicator = false
            )
        }
    }

    fun testGetUserInfo(accessToken : String){
        apiCall(naverOAuthRepository.getUserInfo(accessToken),
        onSuccess = Consumer { it ->
            Log.e("test user info name",it.response.name)

            Log.e("test user info image",it.response.profile_image)
            Log.e("test user info id", it.response.id)
            userProfileImageSrc = it.response.profile_image

            id = it.response.id

            Log.e("test user info email",it.response.email)

            val response =  Response(it.response.age,it.response.birthday,it.response.email,
                it.response.gender,it.response.id,it.response.name,it.response.nickname,it.response.profile_image)


            // set USER ID
//            val body =
//                "\"{\\\"age\\\":${it.response.age},\\\"birthday\\\":${it.response.birthday},\\\"email\\\":${it.response.email},\\\"gender\\\":${it.response.gender},\\\"id\\\":${it.response.id},\\\"name\\\":${it.response.name},\\\"nickname\\\":${it.response.nickname},\\\"profile_image\\\":${it.response.profile_image}}\"".toRequestBody(
//                    "text/plain".toMediaTypeOrNull()
//                )
            //Log.e("리스폰스 스트링", "\"{\"age\":${it.response.age},\"birthday\":${it.response.birthday},\"email\":${it.response.email},\"gender\":${it.response.gender},\"id\":${it.response.id},\"name\":${it.response.name},\"nickname\":${it.response.nickname},\"profile_image\":${it.response.profile_image}}\"")

            val params = HashMap<String, String>()
            params["data"] = Gson().toJson(response)
            apiCall(naverOAuthRepository.postUserInfo(params),
            onSuccess = Consumer {
                stopLoadingIndicator()
                Log.e("서버 전송 완료",it.string())
                _isLoginFinished.call()
            },
                onError = Consumer {
                    stopLoadingIndicator()
                    Log.e("ERROR","ERROR : Post to Server ERROR")
                    _isLoginFailed.call()
            }, indicator = false)
        },
        onError = Consumer {
            stopLoadingIndicator()
            _isLoginFailed.call()
            Log.e("ERROR","ERROR : Get User Info ERROR")
        }, indicator = false)
    }



    fun loadBestSeller(){
        apiCall(jsoupRepository.requestBestSeller(),
            Consumer {
                val doc: Document = Jsoup.parse(it)
                val elements: Elements = doc.select("div[id=section_bestseller] ol").select("li")
                val elemSize = elements.size
                for(elem in elements){
                    val imageSrc = elem.select("div[class=thumb_type thumb_type2] a img").attr("src")
                    val title = elem.select("dt[id=book_title_${popularBookList.size}] a").text()
                    val author = elem.select("dd[class=txt_block] a[class=txt_name N=a:bel.author]").text()
                    popularBookList.add(BestSeller(title,author,imageSrc))
                }
                _isLoadPopularListFinished.call()
            }
            ,onError = Consumer {
                Log.e("ERROR", "Parsing HTMl ERROR!")
            }
            ,indicator = true)
    }

    fun getPopularBookListSize() : Int{
        return popularBookList.size
    }

    fun getPopularBookByPosition(position: Int) : BestSeller{
        return popularBookList[position]
    }

    fun verifyAccessToken(accessToken: String) : Boolean{
        var result : Boolean = false
        apiCall(naverOAuthRepository.verifyAccessToken(accessToken),
        onSuccess = Consumer {
            result = it.resultcode == "00"
        })
        return result
    }

    fun startLoadingIndicator(){
        _startLoadingIndicatorEvent.call()
    }

    fun stopLoadingIndicator(){
        _stopLoadingIndicatorEvent.call()
    }
}
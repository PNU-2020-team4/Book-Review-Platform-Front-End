package com.team4.bookreview.viewModel

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.team4.bookreview.dto.BestSeller
import com.team4.bookreview.dto.Response
import com.team4.bookreview.repository.*
import com.team4.bookreview.utils.SingleLiveEvent
import com.google.gson.Gson
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
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
                    private val module : OAuthLogin,
                    private val preferences: SharedPreferences
) : ViewModel() {
    private val _isLoginFinished: SingleLiveEvent<Any> = SingleLiveEvent()
    val isLoginFinished: LiveData<Any>
        get() = _isLoginFinished
    private val _isPostFinished: SingleLiveEvent<Any> = SingleLiveEvent()
    val isPostFinished: LiveData<Any>
        get() = _isPostFinished
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
    private val _isLoadBestSellerFinished: SingleLiveEvent<Any> = SingleLiveEvent()
    val isLoadBestSellerFinished:LiveData<Any>
        get() = _isLoadBestSellerFinished


    var userProfileImageSrc : String? = null
    var id : String? = null
    var mail : String? = null
    var name : String? = null
    var popularBookList = ArrayList<BestSeller>()

    var bestTitle : String? = null
    var bestAuthor : String? = null
    var bestStar : String? = null
    var bestReviews : String? = null
    var bestImage : String? = null
    var bestLink : String? = null

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

    fun testLogin(activity: Activity){
        module.startOauthLoginActivity(activity, LoginHandler(activity, module, preferences))
    }

    fun getUserInfo(){
        apiCall(naverOAuthRepository.getUserInfo(preferences.getString("ACCESS_TOKEN",null)!!),
        onSuccess = Consumer { it ->
            Log.e("test user info name",it.response.name)
            Log.e("test user info email",it.response.email)
            Log.e("test user info image",it.response.profile_image)
            Log.e("test user info id", it.response.id)

            userProfileImageSrc = it.response.profile_image
            id = it.response.id
            mail = it.response.email
            name = it.response.nickname

            val response =  Response(it.response.age,it.response.birthday,it.response.email,
                it.response.gender,it.response.id,it.response.name,it.response.nickname,it.response.profile_image)


            val params = HashMap<String, String>()
            params["data"] = Gson().toJson(response)
            apiCall(naverOAuthRepository.postUserInfo(params),
            onSuccess = Consumer {
                stopLoadingIndicator()
                Log.e("서버 전송 완료",it.string())
                _isPostFinished.call()
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



    fun loadPopularList(){
        apiCall(jsoupRepository.requestBestSeller(),
            Consumer {
                val doc: Document = Jsoup.parse(it)
                val elements: Elements = doc.select("div[id=section_bestseller] ol").select("li")
                val elemSize = elements.size
                for(elem in elements){
                    val imageSrc = elem.select("div[class=thumb_type thumb_type2] a img").attr("src")
                    val title = elem.select("dt[id=book_title_${popularBookList.size}] a").text()
                    val author = elem.select("dd[class=txt_block] a[class=txt_name N=a:bel.author]").text()
                    val link = elem.select("dl dt[id=book_title_${popularBookList.size}] a").attr("href")
                    popularBookList.add(BestSeller(title,author,imageSrc, link))
                }
                _isLoadPopularListFinished.call()
            }
            ,onError = Consumer {
                Log.e("ERROR", "Parsing HTMl ERROR!")
            }
            ,indicator = true)
    }

    fun loadBestSeller(bid : String){
        apiCall(jsoupRepository.requestResponse(bid),
            Consumer {
                val doc: Document = Jsoup.parse(it)
                val ele : Elements = doc.select("div[class=book_info_inner] div")
                bestAuthor = ele[2].text().substringBefore("|").substringAfter("저자 ")
                bestImage = doc.select("div[class=thumb_type] a img").attr("src")
                var tempText = doc.select("div[class=book_info] h2 a").text()
                var tempTextInSpan = doc.select("div[class=book_info] h2 a span").text()
                bestTitle = tempText.replace(tempTextInSpan, "")
                bestStar = doc.select("a[id=txt_desc_point] strong").text().substringBefore("점 ")
                bestReviews = doc.select("a[id=txt_desc_point] strong").text().substringAfter("점 ")
                bestLink = "https://book.naver.com/bookdb/book_detail.nhn?bid=$bid"
                _isLoadBestSellerFinished.call()
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

    inner class LoginHandler(val context: Context, val module : OAuthLogin, val preferences: SharedPreferences) : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val accessToken = module.getAccessToken(context)
                val refreshToken = module.getRefreshToken(context)
                val expiresAt = module.getExpiresAt(context)
                Log.e("Access Token", module.getAccessToken(context))
                Log.e("Refresh Token", module.getRefreshToken(context))
                Log.e("Expires At", expiresAt.toString())
                preferences.edit().putString("ACCESS_TOKEN", accessToken).apply()
                preferences.edit().putString("REFRESH_TOKEN", refreshToken).apply()
                _isLoginFinished.call()
            } else {
                val errorCode: String =
                    module.getLastErrorCode(context).getCode()
                val errorDesc: String = module.getLastErrorDesc(context)
                Toast.makeText(
                    context, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun startLoadingIndicator(){
        _startLoadingIndicatorEvent.call()
    }

    fun stopLoadingIndicator(){
        _stopLoadingIndicatorEvent.call()
    }
}
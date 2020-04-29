package com.example.bookreview.viewModel

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bookreview.dto.Response
import com.example.bookreview.dto.userInfo
import com.example.bookreview.repository.NaverOAuthRepository
import com.example.bookreview.repository.ServerRepository
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class MainViewModel(private val serverRepository: ServerRepository,
                    private val naverOAuthRepository: NaverOAuthRepository,
                    private val preferences: SharedPreferences
) : ViewModel() {

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
//            .doOnSubscribe{ if(indicator)  }
//            .doAfterTerminate {  }
            .subscribe(onSuccess, onError))
    }

    fun testLoad(){
        apiCall(serverRepository.requestResponse(),
        Consumer {
            Log.e("test",it.responseCode)
        })
    }


    fun testLogin(activity: Activity){
        if(preferences.getString("REFRESH_TOKEN",null) == null){
            apiCall(naverOAuthRepository.getLoginModule(activity),
                Consumer {
                    Log.e("REFRESH TOKEN 없음 : 재요청 결과 access token",it.accessToken)
                    Log.e("REFRESH TOKEN 없음 : 재요청 결과 refresh token",it.refreshToken)
                    testGetUserInfo(it.accessToken)
                })
        }
        else{
            apiCall(naverOAuthRepository.refreshAccessToken(preferences.getString("REFRESH_TOKEN",null)!!),
                onSuccess = Consumer {
                    Log.e("refreshed access token",it.access_token)
                    preferences.edit().putString("ACCESS_TOKEN", it.access_token).apply()
                    testGetUserInfo(it.access_token)
            },
                onError = Consumer {
                    apiCall(naverOAuthRepository.getLoginModule(activity),
                        Consumer {
                            Log.e("refresh token 만료 : 결과 access token",it.accessToken)
                            Log.e("refresh token 만료 : 결과 refresh token",it.refreshToken)
                            testGetUserInfo(it.accessToken)
                        })
                }
            )
        }
    }

    fun testGetUserInfo(accessToken : String){
        apiCall(naverOAuthRepository.getUserInfo(accessToken),
        onSuccess = Consumer { it ->
            Log.e("test user info name",it.response.name)
            Log.e("test user info image",it.response.profile_image)
            Log.e("test user info email",it.response.email)
            val response =  Response(it.response.age,it.response.birthday,it.response.email,
                it.response.gender,it.response.id,it.response.name,it.response.nickname,it.response.profile_image)
//            val body =
//                "\"{\\\"age\\\":${it.response.age},\\\"birthday\\\":${it.response.birthday},\\\"email\\\":${it.response.email},\\\"gender\\\":${it.response.gender},\\\"id\\\":${it.response.id},\\\"name\\\":${it.response.name},\\\"nickname\\\":${it.response.nickname},\\\"profile_image\\\":${it.response.profile_image}}\"".toRequestBody(
//                    "text/plain".toMediaTypeOrNull()
//                )
            //Log.e("리스폰스 스트링", "\"{\"age\":${it.response.age},\"birthday\":${it.response.birthday},\"email\":${it.response.email},\"gender\":${it.response.gender},\"id\":${it.response.id},\"name\":${it.response.name},\"nickname\":${it.response.nickname},\"profile_image\":${it.response.profile_image}}\"")
            val params = HashMap<String, String>()
            params["data"] = Gson().toJson(response)
            apiCall(naverOAuthRepository.postUserInfo(params),
            onSuccess = Consumer {
               Log.e("서버 전송 완료",it.string())
            })
        },
        onError = Consumer {
            Log.e("ERROR","ERROR")
        })
    }

    fun verifyAccessToken(accessToken: String) : Boolean{
        var result : Boolean = false
        apiCall(naverOAuthRepository.verifyAccessToken(accessToken),
        onSuccess = Consumer {
            result = it.resultcode == "00"
        })
        return result
    }
}
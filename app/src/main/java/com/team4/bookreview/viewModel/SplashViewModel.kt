package com.team4.bookreview.viewModel

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.team4.bookreview.dto.Response
import com.team4.bookreview.repository.NaverOAuthRepository
import com.team4.bookreview.repository.ServerRepository
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
import java.util.concurrent.TimeUnit

class SplashViewModel(private val serverRepository: ServerRepository,
                      private val naverOAuthRepository: NaverOAuthRepository,
                      private val module : OAuthLogin,
                      private val preferences: SharedPreferences
) : ViewModel() {

    private val _isLoginNeeded: SingleLiveEvent<Any> = SingleLiveEvent()
    val isLoginNeeded: LiveData<Any>
        get() = _isLoginNeeded

    private val _isLoginFinished: SingleLiveEvent<Any> = SingleLiveEvent()
    val isLoginFinished: LiveData<Any>
        get() = _isLoginFinished

    private val _isPostFinished: SingleLiveEvent<Any> = SingleLiveEvent()
    val isPostFinished: LiveData<Any>
        get() = _isPostFinished

    var userProfileImageSrc : String? = null
    var id : String? = null
    var mail : String? = null
    var name : String? = null

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
            .doOnSubscribe{ }
            .doAfterTerminate { }
            .subscribe(onSuccess, onError))
    }

    //refreshToken 검사
    fun validateRefreshToken(context : Activity) {
        if(preferences.getString("REFRESH_TOKEN",null) == null){
            _isLoginNeeded.call()
        }
        else {
            module.startOauthLoginActivity(context, LoginHandler(context, module, preferences))
            Log.e("TEST", preferences.getString("REFRESH_TOKEN",null)!!)
        }
    }

    fun getUserInfo(){
        val accessToken = preferences.getString("ACCESS_TOKEN",null)
        apiCall(naverOAuthRepository.getUserInfo(accessToken!!),
            onSuccess = Consumer { it ->
                Log.e("test user info name",it.response.name)
                Log.e("test user info email",it.response.email)
                Log.e("test user info image",it.response.profile_image)
                Log.e("test user info id", it.response.id)

                userProfileImageSrc = it.response.profile_image
                id = it.response.id
                mail = it.response.email
                name = it.response.name

                val response =  Response("20-29","12-31",it.response.email,
                    "M",it.response.id,it.response.name,"null",it.response.profile_image)

                val params = HashMap<String, String>()
                params["data"] = Gson().toJson(response)
                apiCall(naverOAuthRepository.postUserInfo(params),
                    onSuccess = Consumer {
                        Log.e("서버 전송 완료",it.string())
                        _isPostFinished.call()
                    },
                    onError = Consumer {
                        Log.e("ERROR","ERROR : Post to Server ERROR")
                        //_isLoginFailed.call()
                    }, indicator = false)
            },
            onError = Consumer {
                //_isLoginFailed.call()
                Log.e("ERROR","ERROR : Get User Info ERROR")
            }, indicator = false)
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
}
package com.example.bookreview.repository

import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.bookreview.api.RefreshService
import com.example.bookreview.api.ServerService
import com.example.bookreview.api.UserService
import com.example.bookreview.dto.*
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody


class NaverOAuthRepositoryImpl constructor(private val userService: UserService,
                                           private val refreshService: RefreshService,
                                           private val serverService: ServerService,
                                            private val preferences: SharedPreferences) : NaverOAuthRepository{
    companion object {
        private var loginInstance:OAuthLogin? = null
        private var handler:OAuthLoginHandler? = null

        fun getInstance(activity: Activity):OAuthLogin{
            if(loginInstance == null){
                loginInstance = OAuthLogin.getInstance()
            }
            return loginInstance!!
        }

        fun getHandler():OAuthLoginHandler{
            if(handler == null){
                handler = object :OAuthLoginHandler(){
                    override fun run(success: Boolean) {

                    }
                }
            }
            return handler!!
        }
    }

    fun getAllToken(activity: Activity){
        val module : OAuthLogin  = OAuthLogin.getInstance()
        module.init(activity, "cQe05FZLRimt6zrnOU14", "X4_MYg00nR", "BookReview")
        val loginHandler = object : OAuthLoginHandler(){
            override fun run(success: Boolean) {
                if(success){
                    val accessToken = module.getAccessToken(activity)
                    val refreshToken = module.getRefreshToken(activity)
                    val expiresAt = module.getExpiresAt(activity)
                    Log.e("Access Token", module.getAccessToken(activity))
                    Log.e("Refresh Token", module.getRefreshToken(activity))
                    Log.e("Expires At", expiresAt.toString())
                    preferences.edit().putString("ACCESS_TOKEN", accessToken)
                    preferences.edit().putString("REFRESH_TOKEN", refreshToken)
                }
                else{
                    val errorCode: String =
                        module.getLastErrorCode(activity).getCode()
                    val errorDesc: String = module.getLastErrorDesc(activity)
                    Toast.makeText(
                        activity, "errorCode:" + errorCode
                                + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        module.startOauthLoginActivity(activity, loginHandler)
    }

    fun login(activity: Activity){
        if(preferences.getString("REFRESH_TOKEN",null) == null){
            getAllToken(activity)
        }
        else{
            //access 토큰으로 정보 가져와서 서버에 post
        }
    }
    override fun getLoginModule(activity: Activity): Single<token> {
        val module : OAuthLogin  = OAuthLogin.getInstance()
        module.init(activity, "cQe05FZLRimt6zrnOU14", "X4_MYg00nR", "BookReview")
        val loginHandler = object : OAuthLoginHandler(){
            override fun run(success: Boolean) {
                if(success){
                    val accessToken = module.getAccessToken(activity)
                    val refreshToken = module.getRefreshToken(activity)
                    val expiresAt = module.getExpiresAt(activity)
                    Log.e("Access Token", module.getAccessToken(activity))
                    Log.e("Refresh Token", module.getRefreshToken(activity))
                    Log.e("Expires At", expiresAt.toString())
                    preferences.edit().putString("ACCESS_TOKEN", accessToken).apply()
                    preferences.edit().putString("REFRESH_TOKEN", refreshToken).apply()
                }
                else{
                    val errorCode: String =
                        module.getLastErrorCode(activity).getCode()
                    val errorDesc: String = module.getLastErrorDesc(activity)
                    Toast.makeText(
                        activity, "errorCode:" + errorCode
                                + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        module.startOauthLoginActivity(activity, loginHandler)
        return Single.fromCallable { token(module.getAccessToken(activity), module.getRefreshToken(activity)) }
    }

    override fun getToken() {

    }

    override fun getUserInfo(accessToken: String): Single<userInfo> {
        return userService.getUserInfo("Bearer $accessToken")
    }

    override fun verifyAccessToken(accessToken: String): Single<verifyToken> {
        return userService.verifyAccessToken("Bearer $accessToken")
    }

    override fun refreshAccessToken(refreshToken: String): Single<RefreshResult> {
        return refreshService.refreshAccessToken(clientID = "cQe05FZLRimt6zrnOU14",
                                                clientSecret = "X4_MYg00nR",
                                                refreshToken = refreshToken,
                                                grantType = "refresh_token")
    }

    override fun postUserInfo(response: Map<String,String>): Single<ResponseBody> {
        return serverService.postUserInfo(response)
    }
}
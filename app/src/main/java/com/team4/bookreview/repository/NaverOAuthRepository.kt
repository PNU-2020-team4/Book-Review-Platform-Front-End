package com.team4.bookreview.repository

import android.app.Activity
import com.team4.bookreview.dto.*
import io.reactivex.Single
import okhttp3.ResponseBody

interface NaverOAuthRepository {
    fun getLoginModule(activity: Activity) : Single<token>
    fun getToken()
    fun getUserInfo(accessToken : String) : Single<userInfo>
    fun verifyAccessToken(accessToken : String) : Single<verifyToken>
    fun refreshAccessToken(refreshToken : String) : Single<RefreshResult>
    fun postUserInfo(response : Map<String,String>) : Single<ResponseBody>
}
package com.team4.bookreview.api

import com.team4.bookreview.dto.userInfo
import com.team4.bookreview.dto.verifyToken
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface UserService {
    @GET("/v1/nid/me")
    fun getUserInfo(@Header("Authorization") accessToken:String) : Single<userInfo>

    @GET("/v1/nid/verify")
    fun verifyAccessToken(@Header("Authorization") accessToken:String) : Single<verifyToken>
}
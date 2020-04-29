package com.example.bookreview.api

import com.example.bookreview.dto.userInfo
import com.example.bookreview.dto.verifyToken
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserService {
    @GET("/v1/nid/me")
    fun getUserInfo(@Header("Authorization") accessToken:String) : Single<userInfo>

    @GET("/v1/nid/verify")
    fun verifyAccessToken(@Header("Authorization") accessToken:String) : Single<verifyToken>
}
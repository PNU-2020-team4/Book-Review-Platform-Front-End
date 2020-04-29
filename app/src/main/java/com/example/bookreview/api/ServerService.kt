package com.example.bookreview.api

import com.example.bookreview.dto.Response
import com.example.bookreview.dto.testClass
import com.example.bookreview.dto.userInfo
import io.reactivex.Single
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ServerService {
    @GET("/ConnectionTest")
    fun getResponse(): Single<testClass>

    @POST("/login")
    @FormUrlEncoded
    fun postUserInfo(@FieldMap(encoded = true) response: Map<String,String>) : Single<ResponseBody>
}
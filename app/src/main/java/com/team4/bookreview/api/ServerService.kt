package com.team4.bookreview.api

import com.team4.bookreview.dto.ServerResponse
import com.team4.bookreview.dto.testClass
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ServerService {
    @GET("/ConnectionTest")
    fun getResponse(): Single<testClass>

    @POST("/login")
    @FormUrlEncoded
    fun postUserInfo(@FieldMap(encoded = true) response: Map<String,String>) : Single<ResponseBody>

    @POST("/review/get")
    @FormUrlEncoded
    fun getReview(@FieldMap(encoded = true) response: Map<String,String>) : Single<ServerResponse>

    @POST("/book/review/get")
    @FormUrlEncoded
    fun getReviewByBook(@FieldMap(encoded = true) response: Map<String, String>) : Single<ServerResponse>

    @POST("/review/insert")
    @FormUrlEncoded
    fun postReview(@FieldMap(encoded = true) response: Map<String,String>) : Single<ServerResponse>

    @POST("/user/withdrawal")
    @FormUrlEncoded
    fun withdrawalUser(@FieldMap(encoded = true) response: Map<String, String>) : Single<ServerResponse>

    @POST("/hist/insert")
    @FormUrlEncoded
    fun addHistory(@FieldMap(encoded = true) response : Map<String, String>) : Single<ServerResponse>

    @POST("/review/delete")
    @FormUrlEncoded
    fun delReview(@FieldMap(encoded = true) response : Map<String, String>) : Single<ServerResponse>

}
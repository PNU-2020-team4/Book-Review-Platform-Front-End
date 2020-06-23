package com.example.bookreview.api

import com.example.bookreview.dto.ServerResponse
import com.example.bookreview.dto.testClass
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

    @POST("/review_item_web/get")
    @FormUrlEncoded
    fun getReview(@FieldMap(encoded = true) response: Map<String,String>) : Single<ServerResponse>

    @POST("/book/review_item_web/get")
    @FormUrlEncoded
    fun getReviewByBook(@FieldMap(encoded = true) response: Map<String, String>) : Single<ServerResponse>

    @POST("/review_item_web/insert")
    @FormUrlEncoded
    fun postReview(@FieldMap(encoded = true) response: Map<String,String>) : Single<ServerResponse>

    @POST("/user/withdrawal")
    @FormUrlEncoded
    fun withdrawalUser(@FieldMap(encoded = true) response: Map<String, String>) : Single<ServerResponse>

    @POST("/hist/insert")
    @FormUrlEncoded
    fun addHistory(@FieldMap(encoded = true) response : Map<String, String>) : Single<ServerResponse>

    @POST("/review_item_web/delete")
    @FormUrlEncoded
    fun delReview(@FieldMap(encoded = true) response : Map<String, String>) : Single<ServerResponse>

}
package com.team4.bookreview.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface JsoupService {
    @GET("/bookdb/book_detail.php")
    fun getResponse(@Query("bid") bid : String): Single<String>

    @GET("/bestsell/bestseller_list.nhn")
    fun getBestSeller(
        @Header("Accept") accept : String,
        @Header("Accept-Language") accept_language : String
    ): Single<String>

    @GET("/bookdb/review.nhn")
    fun getReview(
        @Query("bid") bid: String,
        @Query("page") page : String
    ): Single<String>
}
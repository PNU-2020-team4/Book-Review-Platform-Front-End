package com.example.bookreview.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface JsoupService {
    @GET("/bookdb/book_detail.php")
    fun getResponse(@Query("bid") bid : String): Single<String>
}
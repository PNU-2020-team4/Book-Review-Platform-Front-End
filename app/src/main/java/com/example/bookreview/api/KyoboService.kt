package com.example.bookreview.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface KyoboService {
    @GET("/bestSellerNew/bestseller.laf")
    fun getBestSeller(): Single<String>
}
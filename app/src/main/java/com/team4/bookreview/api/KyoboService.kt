package com.team4.bookreview.api

import io.reactivex.Single
import retrofit2.http.GET

interface KyoboService {
    @GET("/bestSellerNew/bestseller.laf")
    fun getBestSeller(): Single<String>
}
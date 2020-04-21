package com.example.bookreview.api

import com.example.bookreview.dto.testClass
import io.reactivex.Single
import retrofit2.http.GET

interface ServerService {
    @GET("/ConnectionTest")
    fun getResponse(): Single<testClass>
}
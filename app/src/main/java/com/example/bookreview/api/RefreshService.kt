package com.example.bookreview.api

import com.example.bookreview.dto.RefreshResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RefreshService {
    @GET("/oauth2.0/token")
    fun refreshAccessToken(
        @Query("client_id") clientID:String,
        @Query("client_secret") clientSecret:String,
        @Query("refresh_token") refreshToken:String,
        @Query("grant_type") grantType:String
    ) : Single<RefreshResult>
}
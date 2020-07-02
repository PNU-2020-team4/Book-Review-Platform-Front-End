package com.team4.bookreview.api

import com.team4.bookreview.dto.BookSearchResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BookSearchService {
    @GET("/v1/search/book.json")
    fun searchBook(
        @Query("query") title:String,
        @Query("display") displayNum : String,
        @Header("X-Naver-Client-Id") clientID:String,
        @Header("X-Naver-Client-Secret") clientSecret: String
    ) : Single<BookSearchResult>
}
package com.example.bookreview.repository

import com.example.bookreview.book.Book
import com.example.bookreview.dto.ServerResponse
import com.example.bookreview.dto.testClass
import io.reactivex.Single

interface ServerRepository {
    fun requestResponse() : Single<testClass>
    fun getAllReviewResponse() : Single<ServerResponse>
    fun getMyReviewResponse() : Single<ServerResponse>
    fun addHistory(bookTitle:String?, bookAuthor:String?, userID:String?) : Single<ServerResponse>
}
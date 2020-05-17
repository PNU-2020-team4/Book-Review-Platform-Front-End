package com.example.bookreview.repository

import com.example.bookreview.api.BookSearchService
import com.example.bookreview.dto.BookSearchResult
import io.reactivex.Single

class NaverBookSearchRepositoryImpl constructor(private val bookSearchService: BookSearchService) : NaverBookSearchRepository{
    override fun getBookSearchResults(title:String, displayNum:String): Single<BookSearchResult> {
        return bookSearchService.searchBook(title, displayNum, "9oejwsa6a_u3KCnxhEWS", "qkA5MzQhQq")
    }
}
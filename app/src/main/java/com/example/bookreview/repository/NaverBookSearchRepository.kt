package com.example.bookreview.repository

import com.example.bookreview.dto.BookSearchResult
import io.reactivex.Single

interface NaverBookSearchRepository {
    fun getBookSearchResults(title:String, displayNum:String) : Single<BookSearchResult>
}
package com.team4.bookreview.repository

import com.team4.bookreview.dto.BookSearchResult
import io.reactivex.Single

interface NaverBookSearchRepository {
    fun getBookSearchResults(title:String, displayNum:String) : Single<BookSearchResult>
}
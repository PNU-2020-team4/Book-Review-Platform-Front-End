package com.team4.bookreview.repository

import com.team4.bookreview.api.BookSearchService
import com.team4.bookreview.dto.BookSearchResult
import io.reactivex.Single

class NaverBookSearchRepositoryImpl constructor(private val bookSearchService: BookSearchService) : NaverBookSearchRepository{
    override fun getBookSearchResults(title:String, displayNum:String): Single<BookSearchResult> {
        return bookSearchService.searchBook(title, displayNum, "cQe05FZLRimt6zrnOU14", "X4_MYg00nR")
    }
}
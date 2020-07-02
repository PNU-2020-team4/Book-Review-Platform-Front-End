package com.team4.bookreview.dto

data class BookSearchResult(
    val display: Int,
    val items: List<Item>,
    val lastBuildDate: String,
    val start: Int,
    val total: Int
)
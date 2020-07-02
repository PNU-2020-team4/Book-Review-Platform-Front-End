package com.team4.bookreview.dto

data class ReviewFromWeb (
    val user : String,
    val title : String,
    val text : String,
    val thumb : String? = null,
    val url : String,
    val rating : String? = null,
    val date : String
)



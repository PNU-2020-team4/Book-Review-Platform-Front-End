package com.team4.bookreview.dto

data class BestSeller(
    val title : String,
    val author : String,
    val image : String,
    val link : String = ""
)
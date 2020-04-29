package com.example.bookreview.dto

data class RefreshResult(
    val access_token: String,
    val expires_in: String,
    val refresh_token: String,
    val token_type: String
)
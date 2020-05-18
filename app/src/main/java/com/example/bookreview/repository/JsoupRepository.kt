package com.example.bookreview.repository

import io.reactivex.Single

interface JsoupRepository {
    fun requestResponse(bid : String) : Single<String>
}
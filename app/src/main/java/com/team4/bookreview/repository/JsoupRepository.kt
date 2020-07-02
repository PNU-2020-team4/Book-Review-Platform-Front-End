package com.team4.bookreview.repository

import io.reactivex.Single

interface JsoupRepository {
    fun requestResponse(bid : String) : Single<String>
    fun requestBestSeller() : Single<String>
    fun requestReview(bid : String, page : String = "1") : Single<String>
}
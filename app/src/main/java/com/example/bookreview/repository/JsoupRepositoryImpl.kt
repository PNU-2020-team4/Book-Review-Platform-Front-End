package com.example.bookreview.repository

import com.example.bookreview.api.JsoupService
import io.reactivex.Single

class JsoupRepositoryImpl constructor(private val jsoupService: JsoupService) : JsoupRepository{
    override fun requestResponse(bid : String): Single<String> {
        return jsoupService.getResponse(bid)
    }

    override fun requestBestSeller(): Single<String> {
        return jsoupService.getBestSeller()
    }
}
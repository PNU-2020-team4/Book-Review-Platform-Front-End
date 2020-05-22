package com.example.bookreview.repository

import com.example.bookreview.api.JsoupService
import io.reactivex.Single

class JsoupRepositoryImpl constructor(private val jsoupService: JsoupService) : JsoupRepository{
    override fun requestResponse(bid : String): Single<String> {
        return jsoupService.getResponse(bid)
    }

    override fun requestBestSeller(): Single<String> {
        return jsoupService.getBestSeller(accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
            accept_language = "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,zh-CN;q=0.6,zh;q=0.5")
    }
}
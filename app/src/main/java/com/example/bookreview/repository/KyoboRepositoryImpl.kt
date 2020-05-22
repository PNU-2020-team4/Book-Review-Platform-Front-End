package com.example.bookreview.repository

import com.example.bookreview.api.KyoboService
import io.reactivex.Single

class KyoboRepositoryImpl constructor(private val kyoboService: KyoboService) : KyoboRepository{
    override fun requestBestSeller(): Single<String> {
        return kyoboService.getBestSeller()
    }
}
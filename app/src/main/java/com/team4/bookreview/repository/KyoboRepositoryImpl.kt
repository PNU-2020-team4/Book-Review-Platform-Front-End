package com.team4.bookreview.repository

import com.team4.bookreview.api.KyoboService
import io.reactivex.Single

class KyoboRepositoryImpl constructor(private val kyoboService: KyoboService) : KyoboRepository{
    override fun requestBestSeller(): Single<String> {
        return kyoboService.getBestSeller()
    }
}
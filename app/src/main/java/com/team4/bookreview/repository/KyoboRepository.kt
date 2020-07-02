package com.team4.bookreview.repository

import io.reactivex.Single

interface KyoboRepository {
    fun requestBestSeller() : Single<String>
}
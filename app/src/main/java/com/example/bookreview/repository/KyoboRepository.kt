package com.example.bookreview.repository

import io.reactivex.Single

interface KyoboRepository {
    fun requestBestSeller() : Single<String>
}
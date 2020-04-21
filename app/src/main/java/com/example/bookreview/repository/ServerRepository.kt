package com.example.bookreview.repository

import com.example.bookreview.dto.testClass
import io.reactivex.Single

interface ServerRepository {
    fun requestResponse() : Single<testClass>
}
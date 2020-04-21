package com.example.bookreview.repository

import com.example.bookreview.api.ServerService
import com.example.bookreview.dto.testClass
import io.reactivex.Single

class ServerRepositoryImpl constructor(private val serverService: ServerService) : ServerRepository {
    override fun requestResponse(): Single<testClass> {
        return serverService.getResponse()
    }

}
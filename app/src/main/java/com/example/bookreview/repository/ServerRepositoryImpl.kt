package com.example.bookreview.repository

import android.util.Log
import com.example.bookreview.api.ServerService
import com.example.bookreview.book.Book
import com.example.bookreview.dto.ServerResponse
import com.example.bookreview.dto.testClass
import com.google.gson.Gson
import io.reactivex.Single

class ServerRepositoryImpl constructor(private val serverService: ServerService) : ServerRepository {
    override fun requestResponse(): Single<testClass> {
        return serverService.getResponse()
    }

    /* review */
    override fun getAllReviewResponse(): Single<ServerResponse> {
        Log.d("EE", "getAllReviewResponse()")
        val params = HashMap<String, String>()
        params["data"] = Gson().toJson(mapOf("writer" to -1))
        return serverService.getReview(params)
    }

    override fun getMyReviewResponse(): Single<ServerResponse> {
        Log.d("EE", "getAllReviewResponse()")
        val params = HashMap<String, String>()
        // TODO :: need to change
        params["data"] = Gson().toJson(mapOf("writer" to -1))
        return serverService.getReview(params)
    }

    /* book */


    override fun addHistory(bookTitle:String?, bookAuthor:String?, userID:String?): Single<ServerResponse> {
        Log.d("EE", "addHistory()")
        val params = HashMap<String, String>()
        // TODO :: need to change
        params["data"] = Gson().toJson(mapOf("id" to userID, "author" to bookAuthor, "title" to bookTitle))
        return serverService.addHistory(params)
    }
    /* post */

    /* comment */
}
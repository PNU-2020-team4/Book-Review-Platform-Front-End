package com.example.bookreview.repository

import android.util.Log
import com.example.bookreview.api.ServerService
import com.example.bookreview.book.Book
import com.example.bookreview.dto.ServerResponse
import com.example.bookreview.dto.testClass
import com.example.bookreview.ui.review.Review
import com.google.gson.Gson
import io.reactivex.Single

class ServerRepositoryImpl constructor(private val serverService: ServerService) : ServerRepository {
    override fun requestResponse(): Single<testClass> {
        return serverService.getResponse()
    }

    /* review */
    override fun getAllReviewResponse(): Single<ServerResponse> {
        val params = HashMap<String, String>()
        params["data"] = Gson().toJson(mapOf("writer" to -1))
        return serverService.getReview(params)
    }

    override fun getMyReviewResponse(userId: String): Single<ServerResponse> {
        val params = HashMap<String, String>()
        params["data"] = Gson().toJson(mapOf("writer" to userId))
        return serverService.getReview(params)
    }

    override fun getMyReviewResponse(): Single<ServerResponse> {
        TODO("Not yet implemented")
    }

    override fun postMyReviewResponse(review: Review): Single<ServerResponse> {
        val params = HashMap<String, String>()
        params["data"] = Gson().toJson(
                            mapOf(
                                "book" to review.bookId,
                                "writer" to review.writer,
                                "content" to review.content,
                                "star" to review.star
                               ))
        return serverService.postReview(params)
    }

    override fun getWithdrawalResponse(userId: String): Single<ServerResponse> {
        val params = HashMap<String, String>()
        params["data"] = Gson().toJson(mapOf("id" to userId))
        return serverService.withdrawalUser(params)
    }

    /* book + history */

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
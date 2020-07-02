package com.team4.bookreview.repository

import android.util.Log
import com.team4.bookreview.api.ServerService
import com.team4.bookreview.dto.ServerResponse
import com.team4.bookreview.dto.testClass
import com.team4.bookreview.ui.review.Review
import com.google.gson.Gson
import io.reactivex.Single

class ServerRepositoryImpl constructor(private val serverService: ServerService) : ServerRepository {
    override fun requestResponse(): Single<testClass> {
        return serverService.getResponse()
    }

    /* review_item_web */
    override fun getAllReviewResponse(): Single<ServerResponse> {
        val params = HashMap<String, String>()
        Log.e("all review_item_web param : " , params.toString())
        params["data"] = Gson().toJson(mapOf("writer" to -1))
        return serverService.getReview(params)
    }

    override fun getMyReviewResponse(userId: String): Single<ServerResponse> {
        val params = HashMap<String, String>()
        Log.e("param : " , params.toString())
        params["data"] = Gson().toJson(mapOf("writer" to userId))
        return serverService.getReview(params)
    }

    override fun getMyReviewResponse(): Single<ServerResponse> {
        TODO("Not yet implemented")
    }

    override fun getBookReviewResponse(id: Int): Single<ServerResponse> {
        val params = HashMap<String, String>()
        Log.d("param : ", params.toString())
        params["data"] = Gson().toJson(mapOf("bookID" to id))
        return serverService.getReviewByBook(params)
    }

    override fun postMyReviewResponse(review: Review): Single<ServerResponse> {
        val params = HashMap<String, String>()
        params["data"] = Gson().toJson(
                            mapOf(
                                "book" to review.book,
                                "writer" to review.writer,
                                "content" to review.content,
                                "star" to review.star
                               ))
        return serverService.postReview(params)
    }
    override fun delMyReviewResponse(idx: Int): Single<ServerResponse> {
        val params = HashMap<String, String>()
        params["data"] = Gson().toJson(mapOf("idx" to idx))
        val retMsg = serverService.delReview(params)
        Log.e("ret msg : ", retMsg.toString())
        return retMsg

    }

    override fun getWithdrawalResponse(userId: String): Single<ServerResponse> {
        val params = HashMap<String, String>()
        params["data"] = Gson().toJson(mapOf("id" to userId))
        return serverService.withdrawalUser(params)
    }

    /* book + history */

    override fun addHistory(bookIdx:String?, bookTitle:String?, bookAuthor:String?, userID:String?): Single<ServerResponse> {
        Log.d("EE", "addHistory()")
        val params = HashMap<String, String>()
        // TODO :: need to change
        params["data"] = Gson().toJson(mapOf("idx" to bookIdx, "id" to userID, "author" to bookAuthor, "title" to bookTitle))
        return serverService.addHistory(params)
    }
    /* post */

    /* comment */
}

package com.example.bookreview.ui.review

import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*

class Review(
    var idx : String = "",
    var writer : String = "",
    var content : String = "",
    var star : String = "",
    var date : String = "",
    var bookName : String = "",
    var bookAuthor : String = "",
    var nickname: String = "",
    var bookGenre: String = ""
) {
    val pattern = "yyyy-MM-dd"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.KOREA)
    fun jsonToObject(obj: JsonObject): Review {
        obj.get("idx")?.let {
            this.idx = obj.get("idx").asString
        }
        obj.get("writer")?.let {
            this.writer = obj.get("writer").asString
        }
        obj.get("content")?.let {
            this.content = obj.get("content").asString
        }
        obj.get("star")?.let {
            this.star = obj.get("star").asString
        }
        obj.get("date")?.let {

            this.date = simpleDateFormat.format(Date(obj.get("date").asLong))
        }
        obj.get("bookName")?.let {
            this.bookName = obj.get("bookName").asString
        }
        obj.get("bookAuthor")?.let {
            this.bookAuthor = obj.get("bookAuthor").asString
        }
        obj.get("nickname")?.let {
            this.nickname = obj.get("nickname").asString
        }
        obj.get("bookGenre")?.let {
            this.bookGenre = obj.get("bookGenre").asString
        }
        return this
    }
}
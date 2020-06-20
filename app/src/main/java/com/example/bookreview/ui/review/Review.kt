package com.example.bookreview.ui.review

import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*

class Review(
    var idx : String = "",
    var writer : String? = "",
    var content : String? = "",
    var star : String? = "",
    var date : String = "",
    var book : String? = "",
    var bookName : String? = "",
    var bookAuthor : String? = "",
    var nickname: String = "",
    var bookGenre: String? = "",
    var profile_image: String? = ""
) {
    val pattern = "yyyy-MM-dd"
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.KOREA)
    fun jsonToObject(obj: JsonObject): Review {
        obj.get("idx")?.let {
            this.idx = obj.get("idx").toString()
        }
        obj.get("writer")?.let {
            this.writer = obj.get("writer").toString()
        }
        obj.get("content")?.let {
            this.content = obj.get("content").toString().replace("\\n","").replace("\"", "")
        }
        obj.get("star")?.let {
            this.star = obj.get("star").toString()
        }
        obj.get("date")?.let {
            this.date = simpleDateFormat.format(Date(obj.get("date").asLong))
        }
        obj.get("book")?.let {
            this.book = obj.get("book").toString()
        }
        obj.get("bookName")?.let {
            this.bookName = obj.get("bookName").toString().replace("\n","").replace("\"", "")
        }
        obj.get("bookAuthor")?.let {
            this.bookAuthor = obj.get("bookAuthor").toString().replace("\n","").replace("\"", "")
        }
        obj.get("nickname")?.let {
            this.nickname = obj.get("nickname").toString().replace("\"", "")
        }
        obj.get("bookGenre")?.let {
            this.bookGenre = obj.get("bookGenre").toString()
            if (obj.get("bookGenre").toString() == "null") {
                this.bookGenre = ""
            }
        }
        obj.get("img")?.let {
            this.profile_image = obj.get("img").toString().replace("\"", "")
        }
        return this
    }
    override fun toString(): String{
       var message : String = "idx : String = " + idx  +
        " star : String? = " + star;
    return message;
    }
}
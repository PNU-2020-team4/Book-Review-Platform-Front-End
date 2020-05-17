package com.example.bookreview.intent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreview.R
import kotlinx.android.synthetic.main.book.*

class BookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book)

        val thisIntent = intent
        val bookPhoto = thisIntent.getStringExtra("bookPhoto")
        val bookTitle = thisIntent.getStringExtra("bookTitle")
        val bookAuthor = thisIntent.getStringExtra("bookAuthor")
        val bookPrice = thisIntent.getIntExtra("bookPrice", 10000)

        book_photo_Img.setImageResource(resources.getIdentifier(bookPhoto,"drawable", this.packageName.toString()))
        book_title.text = bookTitle
        book_author.text = bookAuthor
        book_price.text = bookPrice.toString()

        book_back_button.setOnClickListener {
            finish()
        }

        review_button.setOnClickListener {
            val nextIntent = Intent(this, ReviewActivity::class.java)
            startActivity(nextIntent)
        }
    }
}
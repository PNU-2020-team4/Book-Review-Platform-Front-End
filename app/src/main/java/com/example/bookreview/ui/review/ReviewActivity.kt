package com.example.bookreview.ui.review

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreview.R
import kotlinx.android.synthetic.main.book_list.*
import kotlinx.android.synthetic.main.review.*

class ReviewActivity : AppCompatActivity() {
    private val reviewList: ArrayList<Review> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review)

        val id = intent.extras?.getString("id")
        val bookId = intent.extras?.getString("bookId")

        val reviewAdapter = ReviewAdapter(
            applicationContext,
            reviewList
        )
        review_recyclerView.adapter = reviewAdapter

        write_review_button.setOnClickListener {
            startActivity(Intent(this, WriteReviewActivity::class.java)
                .putExtra("id", id)
                .putExtra("bookId", bookId)
            )
        }


        /*
        val thisIntent = intent
        //val reviewImg = thisIntent.getStringExtra("bookPhoto")
        val reviewId = thisIntent.getStringExtra("reviewId")
        val reviewDate = thisIntent.getStringExtra("reviewDate")
        val reviewText = thisIntent.getStringExtra("reviewText")

        //review_img.setImageResource(resources.getIdentifier(bookPhoto,"drawable", this.packageName.toString()))
        review_id.text = reviewId
        review_date.text = reviewDate
        review_text.text = reviewText
*/
        review_back_button.setOnClickListener {
            finish()
        }
    }
}
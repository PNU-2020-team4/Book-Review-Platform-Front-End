package com.example.bookreview.ui.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreview.R
import kotlinx.android.synthetic.main.review.*

class ReviewActivity : AppCompatActivity() {
    private val reviewList: ArrayList<Review> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review)

        val reviewAdapter = ReviewAdapter(
            applicationContext,
            reviewList
        )
        review_recyclerView.adapter = reviewAdapter


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
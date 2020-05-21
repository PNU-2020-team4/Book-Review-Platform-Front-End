package com.example.bookreview.ui.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreview.R
import com.example.bookreview.ui.review.Review
import com.example.bookreview.viewModel.ReviewViewModel
import kotlinx.android.synthetic.main.my_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyReviewActivity : AppCompatActivity(){
    private val viewModel by viewModel<ReviewViewModel>()

    private val myReviewList: ArrayList<Review> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_review)

        val userId = intent.extras?.getString("userId")!!
        viewModel.requestMyReviews(userId) {
            it.dataList?.let { list ->
                val numOfReviews = " ( ${list.size()} )"
                textView4.text = numOfReviews

                for (i in 0 until list.size()) {
                    val obj = list[i].asJsonObject
                    myReviewList.add(Review().jsonToObject(obj))
                }
                val myReviewAdapter = MyReviewAdapter(
                    applicationContext,
                    myReviewList
                )
                my_review_recyclerView.adapter = myReviewAdapter
            }
        }



        my_review_back_button.setOnClickListener {
            finish()
        }

    }
}
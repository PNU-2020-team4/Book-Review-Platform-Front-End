package com.example.bookreview.ui.review

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R
import com.example.bookreview.viewModel.ReviewViewModel
import kotlinx.android.synthetic.main.review_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewActivity : AppCompatActivity() {
    private val viewModel by viewModel<ReviewViewModel>()
    private val reviewList: ArrayList<Review> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_list)

        var id = intent.extras?.getString("id")
        var bookId = intent.extras?.getString("bookId")
        var rv = findViewById<RecyclerView>(R.id.review_recyclerView)
        viewModel.requestAllReviews {
            it.dataList?.let { list ->
                val numOfReviews = " ( ${list.size()} )"
//                textView4.text = numOfReviews

                for (i in 0 until list.size()) {
                    val obj = list[i].asJsonObject
                    Log.e("obj string : " , obj.toString())
                    reviewList.add(Review().jsonToObject(obj))
                }
                val reviewAdapter = ReviewAdapter(
                    applicationContext,
                    reviewList,
                    viewModel,
                    this
                )
                review_recyclerView.adapter = reviewAdapter
            }
        }
        rv.setOnClickListener {
            Log.e("abcd : " , "SAdff")
            rv.adapter!!.notifyDataSetChanged()
        }

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
        book_review_back_button.setOnClickListener {
            finish()
        }
        this.window.apply {
            //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }
    }
}
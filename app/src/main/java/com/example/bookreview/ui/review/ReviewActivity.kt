package com.example.bookreview.ui.review

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R
import com.example.bookreview.viewModel.ReviewViewModel
import kotlinx.android.synthetic.main.review_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewActivity : AppCompatActivity() {
    private val viewModel by viewModel<ReviewViewModel>()
    private val reviewList: ArrayList<Review> = ArrayList()
    private lateinit var adapter: ReviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_list)


        var id = intent.extras?.getString("id")
        var bookId = intent.extras?.getString("bookId")
        var rv = findViewById<RecyclerView>(R.id.review_recyclerView)
        viewModel.requestAllReviews {
            Log.e("data list : ", it.toString())
            it.dataList?.let { list ->
                val numOfReviews = " ( ${list.size()} )"
                //textView4.text = numOfReviews
                for (i in 0 until list.size()) {
                    val obj = list[i].asJsonObject
                    Log.e("obj string : ", obj.toString())
                    reviewList.add(Review().jsonToObject(obj))
                }
                val reviewAdapter = ReviewAdapter(
                    applicationContext,
                    reviewList,
                    viewModel,
                    id,
                    this
                )
                review_recyclerView.adapter = reviewAdapter
            }
        }
        rv.setOnClickListener {
            Log.e("abcd : ", "SAdff")
            rv.adapter!!.notifyDataSetChanged()
        }

        write_review_button.setOnClickListener {
            startActivity(
                Intent(this, WriteReviewActivity::class.java)
                    .putExtra("id", id)
                    .putExtra("bookId", bookId)
            )
        }


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

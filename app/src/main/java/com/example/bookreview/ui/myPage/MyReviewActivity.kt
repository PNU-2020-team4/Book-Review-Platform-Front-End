package com.example.bookreview.ui.myPage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
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

        var id = intent.extras?.getString("id")
        var profileImage = intent.extras?.getString("profileImage")
        var rv = findViewById<RecyclerView>(R.id.my_review_recyclerView)
        viewModel.requestMyReviews(id!!) {
            it.dataList?.let { list ->
                val numOfReviews = " ( ${list.size()} )"
//                textView4.text = numOfReviews

                for (i in 0 until list.size()) {
                    val obj = list[i].asJsonObject
                    Log.e("obj string : " , obj.toString())
                    myReviewList.add(Review().jsonToObject(obj))
                }
                val myReviewAdapter = MyReviewAdapter(
                    applicationContext,
                    myReviewList,
                    viewModel
                )
                my_review_recyclerView.adapter = myReviewAdapter
            }
        }
        rv.setOnClickListener {
            Log.e("abcd : " , "SAdff")
            rv.adapter!!.notifyDataSetChanged()
        }


        my_review_back_button.setOnClickListener {
            finish()
        }

    }
}
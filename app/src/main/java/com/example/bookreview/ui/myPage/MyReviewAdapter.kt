package com.example.bookreview.ui.myPage

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R

import com.example.bookreview.ui.review.Review
import com.example.bookreview.viewModel.ReviewViewModel
import kotlinx.android.synthetic.main.my_review.view.*
import kotlinx.android.synthetic.main.review.view.*


class MyReviewAdapter(private val viewModel: ReviewViewModel) : RecyclerView.Adapter<MyReviewAdapter.MyReviewViewHolder>(){
    class MyReviewViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        return MyReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.my_review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        viewModel.getMyReviewByPosition(position).let { review ->
            var bn = review.bookName!!
            if (bn.length > 29) {
                bn = review.bookName!!.substring(0, 15) + "..."
            }
            holder.view.myreview_bookName.text = bn
            holder.view.myreview_ratingBar.rating = review.star?.toFloat()!!
            holder.view.myreview_date.text = review.date
            holder.view.myreview_text.text = review.content
            holder.view.myreview_genre.text = review.bookGenre

            holder.view.myreview_delBtn.setOnClickListener {
                viewModel.delPosition = position
                viewModel.delID = review.bookId
                viewModel.invokeDeleteButtonClick()
            }

            holder.view.myreview_share_button.setOnClickListener {
                viewModel.generateShareText(position)
            }

        }
    }

    override fun getItemCount(): Int = viewModel.getMyReviewListSize()

    private var listener:((item: Review)-> Unit)? = null

    fun setOnItemClickListener(listener: (item:Review) -> Unit) {
        this.listener = listener
    }
}
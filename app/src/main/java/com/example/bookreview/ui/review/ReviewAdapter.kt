package com.example.bookreview.ui.review

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R
import com.example.bookreview.viewModel.ReviewViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.review.view.*

class ReviewAdapter(private val viewModel: ReviewViewModel, private val userid: String) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    class ReviewViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.review, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        viewModel.getMyReviewByPosition(position).let { review ->
            holder.view.review_user_name.text = review.nickname
            holder.view.review_date.text = review.date
            holder.view.review_ratingBar.rating = review.star!!.toFloat()

            var rt = review.content!!
            if (rt.length > 100) {
                rt = review.content!!.substring(0, 100) + "..."
            }

            holder.view.review_text.text = rt
            Picasso.get().load(review.profile_image).into(holder.view.review_user_image)

            if (userid == review.writer) {
                holder.view.review_del_button.visibility = View.VISIBLE
            } else {
                holder.view.review_del_button.visibility = View.GONE
            }

            holder.view.review_del_button.setOnClickListener {
                viewModel.delPosition = position
                viewModel.delID = review.idx
                viewModel.delReviewWriter = review.writer
                viewModel.invokeDeleteButtonClick()
            }

            holder.view.review_share_button.setOnClickListener {
                viewModel.generateShareText(position)
            }
        }

//        holder.itemView.setOnClickListener {
//            val nextIntent = Intent(context, SelectedReviewActivity()::class.java)
//                .putExtra("reviewStar", review.star)
//                .putExtra("reviewText", review.content)
//            context.startActivity(nextIntent.addFlags(FLAG_ACTIVITY_NEW_TASK))
//        }
    }

    override fun getItemCount(): Int = viewModel.getMyReviewListSize()
}
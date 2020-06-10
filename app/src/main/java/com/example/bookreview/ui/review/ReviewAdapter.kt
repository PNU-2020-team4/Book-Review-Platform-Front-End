package com.example.bookreview.ui.review

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R
import com.example.bookreview.viewModel.ReviewViewModel
import com.squareup.picasso.Picasso

class ReviewAdapter(private val context: Context, private val reviewList: ArrayList<Review>, private val viewModel: ReviewViewModel, private val ctx:Context) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ReviewViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review : Review = reviewList[position]
        holder.bind(review)

        holder.itemView.setOnClickListener {
            val nextIntent = Intent(context, SelectedReviewActivity()::class.java)
                .putExtra("reviewStar", review.star)
                .putExtra("reviewText", review.content)
            context.startActivity(nextIntent.addFlags(FLAG_ACTIVITY_NEW_TASK))
        }
    }

    override fun getItemCount(): Int = reviewList.size

    inner class ReviewViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.review, parent, false)){
        //private var reviewImg : ImageView? = null
        //private var reviewBookName : TextView? = null
        private var reviewDate : TextView? = null
        private var reviewText : TextView? = null
        private var reviewRating : RatingBar? = null
        private var reviewId : TextView? = null
        private var reviewUserImg : ImageView ?= null

        init{
            reviewId = itemView.findViewById(R.id.review_user_name)
            reviewDate = itemView.findViewById(R.id.review_date)
            reviewText = itemView.findViewById(R.id.review_text)
            reviewRating = itemView.findViewById(R.id.review_ratingBar)
            reviewUserImg = itemView.findViewById(R.id.review_user_image)
        }

        fun bind(review: Review){
            //reviewBookName?.text = review.bookName
            reviewRating?.rating = review.star!!.toFloat()
            reviewDate?.text = review.date
            reviewId?.text = review.nickname
            //Picasso.get().load(review.img).into(reviewUserImg)

            var rt = review.content!!
            if(rt.length > 100){
                rt = review.content!!.substring(0, 100) + "..."
            }
            reviewText?.text = rt

            itemView.setOnClickListener {  }
        }
    }
}
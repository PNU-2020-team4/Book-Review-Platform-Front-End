package com.example.bookreview.ui.review

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R

class ReviewAdapter(private val context: Context, private val reviewList: ArrayList<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){

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
            val nextIntent = Intent(context, SelectedReviewActivity::class.java)
            context.startActivity(nextIntent)
        }
    }

    override fun getItemCount(): Int = reviewList.size

    class ReviewViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.review_list, parent, false)){
        //private var reviewImg : ImageView? = null
        private var reviewBookName : TextView? = null
        private var reviewDate : TextView? = null
        private var reviewText : TextView? = null
        private var reviewRating : RatingBar? = null

        init{
            reviewBookName = itemView.findViewById(R.id.review_bookName)
            reviewDate = itemView.findViewById(R.id.review_date)
            reviewText = itemView.findViewById(R.id.review_text)
            reviewRating = itemView.findViewById(R.id.ratingBar)
            //reviewImg = itemView.findViewById(R.id.review_id)
        }

        fun bind(review: Review){
            reviewBookName?.text = review.bookName
            reviewRating?.rating = review.star!!.toFloat()

            reviewDate?.text = review.date
            reviewText?.text = review.content
        }
    }
}
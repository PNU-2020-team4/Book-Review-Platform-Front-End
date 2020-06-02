package com.example.bookreview.ui.myPage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R
import com.example.bookreview.ui.review.Review

class MyReviewAdapter(private val context: Context, private val myReviewList: ArrayList<Review>, private val id : String?) : RecyclerView.Adapter<MyReviewAdapter.MyReviewViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyReviewViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        val review : Review = myReviewList[position]
        holder.bind(review)
    }

    override fun getItemCount(): Int = myReviewList.size

    inner class MyReviewViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.review_list, parent, false)){
        //private var reviewImg : ImageView? = null
        private var reviewBookName : TextView? = null
        private var reviewDate : TextView? = null
        private var reviewText : TextView? = null
        private var reviewRating : RatingBar? = null
        private var reviewGenre : TextView? = null
        private var delBtn : Button? = null

        init{
            reviewBookName = itemView.findViewById(R.id.review_bookName)
            reviewDate = itemView.findViewById(R.id.review_date)
            reviewText = itemView.findViewById(R.id.review_text)
            reviewRating = itemView.findViewById(R.id.ratingBar)
            reviewGenre = itemView.findViewById(R.id.review_genre)
            delBtn = itemView.findViewById(R.id.delBtn)
            //reviewImg = itemView.findViewById(R.id.review_id)
        }

        fun bind(review: Review){
            reviewBookName?.text = review.bookName
            reviewRating?.rating = review.star.toFloat()

            reviewDate?.text = review.date
            reviewText?.text = review.content
            reviewGenre?.text = review.bookGenre
            delBtn?.setOnClickListener {
                delBtn?.text = "del"
            }
        }
    }
}
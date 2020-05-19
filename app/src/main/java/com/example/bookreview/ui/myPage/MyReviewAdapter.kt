package com.example.bookreview.ui.myPage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R
import com.example.bookreview.ui.review.Review

class MyReviewAdapter(private val context: Context, private val myReviewList: ArrayList<Review>) : RecyclerView.Adapter<MyReviewAdapter.MyReviewViewHolder>(){

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

    class MyReviewViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.review_list, parent, false)){
        //private var reviewImg : ImageView? = null
        private var reviewId : TextView? = null
        private var reviewDate : TextView? = null
        private var reviewText : TextView? = null

        init{
            reviewId = itemView.findViewById(R.id.review_id)
            reviewDate = itemView.findViewById(R.id.review_date)
            reviewText = itemView.findViewById(R.id.review_text)
            //reviewImg = itemView.findViewById(R.id.review_id)
        }

        fun bind(review: Review){
            reviewId?.text = review.id
            reviewDate?.text = review.date
            reviewText?.text = review.review_text
        }
    }
}
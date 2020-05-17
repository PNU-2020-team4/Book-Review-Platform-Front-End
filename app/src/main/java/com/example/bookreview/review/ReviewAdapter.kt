package com.example.bookreview.review

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R
import com.example.bookreview.intent.ReviewActivity

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
            val nextIntent = Intent(context, ReviewActivity::class.java)
            //nextIntent.putExtra("reviewImg", review.img)
            nextIntent.putExtra("reviewId", review.id)
            nextIntent.putExtra("reviewDate", review.date)
            nextIntent.putExtra("reviewText", review.review_text)
            context.startActivity(nextIntent)
        }
    }

    override fun getItemCount(): Int = reviewList.size

    class ReviewViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.review_list, parent, false)){
        //private var reviewImg : ImageView? = null
        private var reviewId : TextView? = null
        private var reviewDate : TextView? = null
        private var reviewText :TextView? = null

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
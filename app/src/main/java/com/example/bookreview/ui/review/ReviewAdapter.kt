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

class ReviewAdapter(private val context: Context, private val reviewList: ArrayList<Review>, private val viewModel: ReviewViewModel, private val id: String?, private val ctx:Context) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>(){

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
        private var reviewDelBtn : ImageButton?= null

        init{
            reviewId = itemView.findViewById(R.id.review_user_name)
            reviewDate = itemView.findViewById(R.id.review_date)
            reviewText = itemView.findViewById(R.id.review_text)
            reviewRating = itemView.findViewById(R.id.review_ratingBar)
           reviewUserImg = itemView.findViewById(R.id.review_user_image)
           reviewDelBtn = itemView.findViewById(R.id.review_del_button)
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

            reviewDelBtn?.setOnClickListener {
                Log.e("writer : ", review.writer)
                val builder = AlertDialog.Builder(ctx)
                var writer = review.writer

                if (writer?.toInt() == id?.toInt()) {
                    builder.setTitle("삭제 확인")
                    builder.setMessage("정말 삭제 하시겠습니까?")
                    builder.setPositiveButton("삭제") { dialog, which ->
                        Toast.makeText(ctx, "삭제되었습니다.", Toast.LENGTH_LONG).show()
                        val idx = review.idx
                        Log.e("idx : ", idx)
                        viewModel.delReview(idx!!.toInt()) {}
                        reviewList.remove(review)
                        notifyDataSetChanged()

                    }
                    builder.setNegativeButton("취소") { dialog, which ->
                        Toast.makeText(ctx, "취소하였습니다.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    builder.setTitle("삭제 오류")
                    builder.setMessage("자신이 작성한 리뷰만 삭제할 수 있습니다.")
                    builder.setPositiveButton("확인") { dialog, which ->
                        Toast.makeText(ctx, "삭제할 수 없습니다.", Toast.LENGTH_LONG).show()
                    }
                }

                val dialog: AlertDialog = builder.create()

                dialog.show()
            }
            itemView.setOnClickListener {  }
        }
    }
}


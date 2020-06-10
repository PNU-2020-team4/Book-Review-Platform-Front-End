package com.example.bookreview.ui.myPage

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R

import com.example.bookreview.ui.review.Review
import com.example.bookreview.viewModel.ReviewViewModel


class MyReviewAdapter(private val context: Context, private val myReviewList: ArrayList<Review>, private val viewModel: ReviewViewModel, private val ctx:Context) : RecyclerView.Adapter<MyReviewAdapter.MyReviewViewHolder>(){
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

    private var listener:((item: Review)-> Unit)? = null

    fun setOnItemClickListener(listener: (item:Review) -> Unit) {
        this.listener = listener
    }

    inner class MyReviewViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(
        R.layout.my_review, parent, false)){
        //private var reviewImg : ImageView? = null
        private var reviewBookName : TextView? = null
        private var reviewDate : TextView? = null
        private var reviewText : TextView? = null
        private var reviewRating : RatingBar? = null
        private var reviewGenre : TextView? = null
        private var delBtn : ImageButton? = null
        private var shareBtn : ImageButton? = null

        init{
            reviewBookName = itemView.findViewById(R.id.review_bookName)
            reviewDate = itemView.findViewById(R.id.review_date)
            reviewText = itemView.findViewById(R.id.review_text)
            reviewRating = itemView.findViewById(R.id.ratingBar)
            reviewGenre = itemView.findViewById(R.id.review_genre)
            delBtn = itemView.findViewById(R.id.delBtn)
            shareBtn = itemView.findViewById(R.id.my_comment_share_button)

            //reviewImg = itemView.findViewById(R.id.review_id)

        }

        fun bind(review: Review){
            var bn = review.bookName!!
            if (bn.length > 29) {
                bn = review.bookName!!.substring(0, 15) + "..."
            }
            reviewBookName?.text = bn

            reviewRating?.rating = review.star?.toFloat()!!
            reviewDate?.text = review.date
            reviewText?.text = review.content
            reviewGenre?.text = ""
            if (review.bookGenre != null) {
                reviewGenre?.text = review.bookGenre
            }

            delBtn?.setOnClickListener {
                showSettingPopup(review)
            }

            shareBtn?.setOnClickListener {
                var text = "제목 : ${review.bookName}\n"
                if (review.bookGenre != null) {
                    text += "장르 : ${review.bookGenre}\n"
                }
                text += review.content
                text.replace("<b>", "")
                text.replace("</b>", "")

                val sendIntent:Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                }
                Log.e("In comment share : ", "after make sendIntent")
                val shareIntent = Intent.createChooser(sendIntent, null)
                Log.e("In comment share : ", "after make shareIntent")
                context.startActivity(shareIntent.addFlags(FLAG_ACTIVITY_NEW_TASK))
            }
        }
        fun showSettingPopup(review: Review) {

            val builder = AlertDialog.Builder(ctx)

            builder.setTitle("삭제 확인")
            builder.setMessage("정말 삭제 하시겠습니까?")
            builder.setPositiveButton("삭제") {dialog, which ->
                Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_LONG).show()
                val idx = review.idx
                viewModel.delMyReview(idx.toInt()){}
                myReviewList.removeAt(absoluteAdapterPosition)
                notifyDataSetChanged()
            }

            builder.setNegativeButton("취소") {dialog, which ->
                Toast.makeText(context, "취소하였습니다", Toast.LENGTH_LONG).show()
            }

            val dialog:AlertDialog = builder.create()

            dialog.show()

        }



    }
}
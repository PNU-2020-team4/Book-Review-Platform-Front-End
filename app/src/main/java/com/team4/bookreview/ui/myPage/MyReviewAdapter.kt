package com.team4.bookreview.ui.myPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.team4.bookreview.R

import com.team4.bookreview.ui.review.Review
import com.team4.bookreview.viewModel.ReviewViewModel
import kotlinx.android.synthetic.main.my_review.view.*


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
                viewModel.delID = review.idx
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
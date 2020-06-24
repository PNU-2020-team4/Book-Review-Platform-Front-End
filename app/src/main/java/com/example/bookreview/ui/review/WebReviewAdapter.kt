package com.example.bookreview.ui.review

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R
import com.example.bookreview.viewModel.ReviewViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.review_item_web.view.*

class WebReviewAdapter(val context: Context, private val viewModel: ReviewViewModel) : RecyclerView.Adapter<WebReviewAdapter.ReviewViewHolder>() {
    class ReviewViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.review_item_web, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        viewModel.getWebReviewByPosition(position).let { item ->
            if(item.rating != null){
                holder.view.review_rating_star.visibility = View.VISIBLE
                holder.view.review_rating_number.visibility = View.VISIBLE
                holder.view.review_date.visibility = View.VISIBLE
                holder.view.review_separator.visibility = View.VISIBLE
                holder.view.review_user.visibility = View.VISIBLE
                holder.view.review_date_n.visibility = View.GONE
                holder.view.review_separator_n.visibility = View.GONE
                holder.view.review_user_n.visibility = View.GONE

                holder.view.review_user.text = item.user
                holder.view.review_date.text = item.date
                holder.view.review_rating_number.text = item.rating
                holder.view.review_rating_star.rating = (item.rating.toFloat()/2.0).toFloat()

            }
            else{
                holder.view.review_rating_star.visibility = View.GONE
                holder.view.review_rating_number.visibility = View.GONE
                holder.view.review_date.visibility = View.GONE
                holder.view.review_separator.visibility = View.GONE
                holder.view.review_user.visibility = View.GONE
                holder.view.review_date_n.visibility = View.VISIBLE
                holder.view.review_separator_n.visibility = View.VISIBLE
                holder.view.review_user_n.visibility = View.VISIBLE

                holder.view.review_date_n.text = item.date
                holder.view.review_user_n.text = item.user
            }
            holder.view.review_text.text = item.text
            holder.view.review_title.text = item.title
            val url = item.url
            Log.e("리뷰 타이틀", item.title)
            if(item.thumb != null && item.thumb.trim().isNotEmpty()){
                holder.view.review_thumb.visibility = View.VISIBLE
                Picasso.get().load(item.thumb).into(holder.view.review_thumb)
            }
            else{
                holder.view.review_thumb.visibility = View.GONE
            }
            holder.view.setOnClickListener {
                val nextIntent = Intent(context, ReviewWebViewActivity::class.java)
                    .putExtra("url", url)
                context.startActivity(nextIntent)
            }
        }

    }

    override fun getItemCount(): Int {
        return viewModel.getWebReviewListSize()
    }

}
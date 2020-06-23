package com.example.bookreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.viewModel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.popular_item.view.*

class MainBestSellerAdapter (private val viewModel: MainViewModel): RecyclerView.Adapter<MainBestSellerAdapter.ViewHolder>() {
    class ViewHolder(val view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_item, parent, false))
    }

    override fun getItemCount(): Int {
        return viewModel.getPopularBookListSize()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        viewModel.getPopularBookByPosition(position).let {item ->
            if(item.image != ""){
                Picasso.get().load(item.image).into(holder.view.popular_item_image)
            }
            holder.view.popular_item_title.text = item.title
            holder.view.popular_item_author.text = item.author
            holder.view.setOnClickListener {

            }
        }
    }
}
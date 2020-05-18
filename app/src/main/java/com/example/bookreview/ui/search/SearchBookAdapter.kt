package com.example.bookreview.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R
import com.example.bookreview.viewModel.SearchViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.search_result_item.view.*

class SearchBookAdapter(private val viewModel: SearchViewModel) : RecyclerView.Adapter<SearchBookAdapter.ViewHolder>() {

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchBookAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false))
    }

    override fun getItemCount(): Int {
        return viewModel.getBookListSize()
    }

    override fun onBindViewHolder(holder: SearchBookAdapter.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            viewModel.getBookByPosition(position).let {item ->
                var title = item.title
                if(item.image != ""){
                    Picasso.get().load(item.image).into(holder.view.searchResultImage)
                }
                title = title.replace("<b>","")
                title = title.replace("</b>","")
                holder.view.searchResultTitle.text = title
                holder.view.searchResultAuthor.text = item.author
                holder.view.searchResultPublish.text = item.publisher
                holder.view.setOnClickListener {
                    viewModel.clickedBid = item.link.substringAfter("?bid=")
                    viewModel.imageUrl = item.image
                    viewModel.title = title
                    viewModel.author = item.author
                    viewModel.price = item.price
                    viewModel.invokeClick()
                }
            }
        }
    }
}
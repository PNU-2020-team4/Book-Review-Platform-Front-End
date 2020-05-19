package com.example.bookreview.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.bookreview.R
import com.example.bookreview.ui.book.BookInformationActivity
import com.example.bookreview.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var adapter: SearchBookAdapter

    override fun onCreate(savedInstanceState: Bundle? ){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        adapter = SearchBookAdapter(viewModel)
        search_auto_complete.adapter = adapter

        search_back_button.setOnClickListener {
            finish()
        }

        viewModel.isBookSearchLoaded.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.isBookClicked.observe(this, Observer {
            startActivity(Intent(this, BookInformationActivity::class.java)
                .putExtra("bid", viewModel.clickedBid)
                .putExtra("imageUrl", viewModel.imageUrl)
                .putExtra("title", viewModel.title)
                .putExtra("author", viewModel.author)
                .putExtra("price", viewModel.price))
        })
        search_box_book.let { searchView ->
            searchView.setOnClickListener {
                searchView.isIconified = false
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText != "") viewModel.bookSearch(newText!!)
                    else {
                        viewModel.searchListClear()
                        adapter.notifyDataSetChanged()
                    }
                    return true
                }
            })
        }
    }
}
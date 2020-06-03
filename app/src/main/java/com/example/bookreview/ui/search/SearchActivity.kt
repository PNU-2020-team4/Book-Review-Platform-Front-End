package com.example.bookreview.ui.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.bookreview.R
import com.example.bookreview.ui.book.BookInformationActivity
import com.example.bookreview.viewModel.HistoryViewModel
import com.example.bookreview.viewModel.SearchViewModel
import kotlinx.android.synthetic.main.search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {
    private val viewModel by viewModel<SearchViewModel>()
    private val historyViewModel by viewModel<HistoryViewModel>()

    private lateinit var adapter: SearchBookAdapter
    private var userID : String? = null


    override fun onCreate(savedInstanceState: Bundle? ){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        if (intent.hasExtra("ID")) {
            userID = intent.getStringExtra("ID")

        } else {
            Toast.makeText(this, "전달된 사용자 아이디가 없습니다", Toast.LENGTH_SHORT).show()
        }

        adapter = SearchBookAdapter(viewModel)
        search_auto_complete.adapter = adapter

        search_back_button.setOnClickListener {
            finish()
        }



        viewModel.isBookSearchLoaded.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })


        viewModel.isBookClicked.observe(this, Observer {

            historyViewModel.addMyHistory(viewModel.clickedBid, viewModel.title, viewModel.author, userID) {
                it.resultCode?.let {code->
                    Log.d("Result Code", code.toString())
                }
            }

            startActivity(Intent(this, BookInformationActivity::class.java)
                .putExtra("uid", userID)
                .putExtra("bid", viewModel.clickedBid)
                .putExtra("imageUrl", viewModel.imageUrl)
                .putExtra("title", viewModel.title)
                .putExtra("author", viewModel.author)
                .putExtra("price", viewModel.price)
                .putExtra("link", viewModel.link))
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

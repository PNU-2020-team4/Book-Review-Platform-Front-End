package com.example.bookreview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle? ){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)
        my_page_back_button.setOnClickListener {
            finish()
        }
    }
}
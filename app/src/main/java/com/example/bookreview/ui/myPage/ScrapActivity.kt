package com.example.bookreview.ui.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreview.R
import kotlinx.android.synthetic.main.scrap.*

class ScrapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scrap)

        scrap_back_button.setOnClickListener {
            finish()
        }
    }
}
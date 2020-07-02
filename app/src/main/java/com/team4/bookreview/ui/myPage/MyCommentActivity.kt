package com.team4.bookreview.ui.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.team4.bookreview.R
import kotlinx.android.synthetic.main.my_comment.*

class MyCommentActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_comment)

        my_comment_back_button.setOnClickListener {
            finish()
        }
    }
}
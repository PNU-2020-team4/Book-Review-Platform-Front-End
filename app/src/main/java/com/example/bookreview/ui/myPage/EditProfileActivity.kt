package com.example.bookreview.ui.myPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreview.R
import kotlinx.android.synthetic.main.edit_profile.*

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile)

        edit_profile_back_button.setOnClickListener {
            finish()
        }
    }
}
package com.example.bookreview.ui.myPage

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreview.R
import com.example.bookreview.viewModel.MainViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.my_page.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)
        this.window.apply {
            //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }
        var id = intent.extras?.getString("id")
        var mail = intent.extras?.getString("mail")
        var name = intent.extras?.getString("nickname")
        var profileImage = intent.extras?.getString("profileImage")
        Picasso.get().load(profileImage).into(my_page_user_image)

        my_page_profile_photo.elevation = 10.0f
        my_page_user_name.text = name
        my_page_user_email.text = mail

        my_page_back.setOnClickListener {
            finish()
        }

        my_page_my_review.setOnClickListener {
            val nextIntent = Intent(this, MyReviewActivity::class.java)
            nextIntent.putExtra("id", id)
            nextIntent.putExtra("profileImage", profileImage)
            startActivity(nextIntent)
        }
//
//        edit_profile_button.setOnClickListener {
//            startActivity(Intent(this, EditProfileActivity::class.java))
//        }

        my_page_my_comment.setOnClickListener {
            startActivity(Intent(this, MyCommentActivity::class.java))
        }

        my_page_my_scrap.setOnClickListener {
            startActivity(Intent(this, ScrapActivity::class.java))
        }

        my_page_logout.setOnClickListener {
            val nextIntent = Intent(this, WithdrawalActivity::class.java)
            nextIntent.putExtra("userId", id)
            startActivity(nextIntent)
        }
    }
}
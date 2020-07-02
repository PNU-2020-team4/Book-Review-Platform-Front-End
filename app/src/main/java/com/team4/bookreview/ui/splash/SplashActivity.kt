package com.team4.bookreview.ui.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.team4.bookreview.LoginActivity
import com.team4.bookreview.MainActivity
import com.team4.bookreview.R
import com.team4.bookreview.viewModel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {
    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel.validateRefreshToken(this)

        viewModel.isLoginNeeded.observe(this, Observer {
            startActivity(Intent(applicationContext, LoginActivity::class.java))

            finish()
        })

        viewModel.isLoginFinished.observe(this, Observer {
            viewModel.getUserInfo()
        })

        viewModel.isPostFinished.observe(this, Observer {
            startActivity(Intent(applicationContext, MainActivity::class.java)
                .putExtra("profileImage", viewModel.userProfileImageSrc)
                .putExtra("id", viewModel.id)
                .putExtra("mail", viewModel.mail)
                .putExtra("nickname", viewModel.name))


            finish()
        })

        this.window.apply {
            //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }

    }
    
}
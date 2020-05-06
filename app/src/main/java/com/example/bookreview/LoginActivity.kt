package com.example.bookreview

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bookreview.viewModel.MainViewModel
import kotlinx.android.synthetic.main.login.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        buttonOAuthLoginImg.setOnClickListener {
            viewModel.testLogin(this)
        }
        viewModel.isLoginFinished.observe(this, Observer {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        })
    }
}
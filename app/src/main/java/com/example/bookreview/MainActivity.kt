package com.example.bookreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bookreview.viewModel.MainViewModel
import com.nhn.android.naverlogin.OAuthLogin
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //viewModel.testLoad()
        viewModel.testLogin(this)
    }
}

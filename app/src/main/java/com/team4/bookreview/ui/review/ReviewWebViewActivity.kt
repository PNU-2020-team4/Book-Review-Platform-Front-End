package com.team4.bookreview.ui.review

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.team4.bookreview.R
import kotlinx.android.synthetic.main.activity_web_view.*

class ReviewWebViewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = intent.extras?.getString("url")
        Log.e("url",url)

        webview.settings.let {
            it.javaScriptEnabled = true
            it.javaScriptCanOpenWindowsAutomatically = true
            it.setSupportZoom(true)
            it.blockNetworkImage = false
            it.loadsImagesAutomatically = true
            it.domStorageEnabled = true
        }

        webview.fitsSystemWindows = true
        webview.loadUrl(url)
        webview.webViewClient = WebViewClient()

        this.window.apply {
            //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }
    }

}
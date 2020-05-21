package com.example.bookreview

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bookreview.utils.LoadingIndicator
import com.example.bookreview.viewModel.MainViewModel
import kotlinx.android.synthetic.main.login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private var mLoadingIndicator: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        loadingIndicatorObserving()
        buttonOAuthLoginImg.setOnClickListener {
            viewModel.testLogin(this)
        }
        viewModel.isLoginFailed.observe(this, Observer {
            AlertDialog.Builder(this).create().let {
                it.setTitle("서버 통신 에러!")
                it.setMessage("로그인 데이터 서버 전송에 실패하였습니다!\n 네트워크 상태를 확인해 주세요.")
                it.setButton(AlertDialog.BUTTON_NEUTRAL, "확인"
                ) { dialog, _ ->
                    dialog.dismiss()
                    finish()
                }
                it.show()
            }
        })
        viewModel.isLoginFinished.observe(this, Observer {
            startActivity(Intent(applicationContext, MainActivity::class.java)
                .putExtra("profileImage", viewModel.userProfileImageSrc)
                .putExtra("userId", viewModel.userId))
            finish()
        })
        this.window.apply {
            //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }
    }

    private fun loadingIndicatorObserving() {
        viewModel.startLoadingIndicatorEvent.observe(this, Observer {
            startLoadingIndicator()
        })
        viewModel.stopLoadingIndicatorEvent.observe(this, Observer {
            stopLoadingIndicator()
        })
    }

    private fun stopLoadingIndicator() {
        mLoadingIndicator?.let {
            if (it.isShowing) it.cancel()
        }
    }

    private fun startLoadingIndicator() {
        stopLoadingIndicator()
        if (!isFinishing) {
            mLoadingIndicator = LoadingIndicator(this)
            mLoadingIndicator?.show()
        }
    }
}
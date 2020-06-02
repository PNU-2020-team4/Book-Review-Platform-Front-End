package com.example.bookreview.ui.myPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bookreview.R
import com.example.bookreview.viewModel.MypageViewModel
import kotlinx.android.synthetic.main.activity_withdrawal.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WithdrawalActivity : AppCompatActivity() {
    private val viewModel by viewModel<MypageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withdrawal)

        val userId = intent.extras?.getString("userId")!!

        withdrawal_button.setOnClickListener {
            viewModel.requestWithdrawal(userId) {
                it.dataObject?.let { json ->
                    val responseId = json.get("userId").asString
                    if (responseId == userId) {
                        // 회원 탈퇴 완료
                        
                    } else {
                        // 회원 탈퇴 실패
                        Toast.makeText(this@WithdrawalActivity, "회원 탈퇴에 실패했습니다\n잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }

        withdrawal_back_button.setOnClickListener {
            finish()
        }
    }
}

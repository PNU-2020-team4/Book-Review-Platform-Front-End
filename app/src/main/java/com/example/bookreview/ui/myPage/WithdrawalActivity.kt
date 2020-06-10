package com.example.bookreview.ui.myPage

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
            val builder = AlertDialog.Builder(this)
            builder.setTitle("회원탈퇴")
            builder.setMessage("확인 버튼을 누르면 회원 탈퇴가 왼료됩니다.\n회원탈퇴를 진행 하시겠습니까?")
            builder.setPositiveButton("탈퇴", DialogInterface.OnClickListener { dialog, which ->
                requestWithdrawal(userId) {
                    dialog.dismiss()
                }
            })
            builder.show()
        }

        withdrawal_back_button.setOnClickListener {
            finish()
        }
    }
    fun requestWithdrawal(userId: String, success:() -> Unit) {
        viewModel.requestWithdrawal(userId) {
            it.dataObject?.let { json ->
                val responseId = json.get("userId").toString()
                if (responseId == userId) {
                    success()
                    Toast.makeText(this@WithdrawalActivity, "회원 탈퇴에 성공했습니다\n앱을 종료합니다", Toast.LENGTH_SHORT).show()
                } else {
                    // 회원 탈퇴 실패
                    Toast.makeText(this@WithdrawalActivity, "회원 탈퇴에 실패했습니다\n잠시 후 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}

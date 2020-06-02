package com.example.bookreview.ui.review

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreview.R
import kotlinx.android.synthetic.main.selected_review.*

class SelectedReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selected_review)

        /*if(현재 유저 == 리뷰 작성자){
            //현재 유저가 리뷰 작성자일 경우 수정 버튼, 삭제 버튼 보이도록 설정
            selected_review_editText.isClickable = true
            selected_review_modify_button.visibility = View.VISIBLE
            selected_review_star.isClickable = true
            floatingDeleteButton.visibility = View.VISIBLE
        }*/

        selected_review_back_button.setOnClickListener {
            finish()
        }
    }
}
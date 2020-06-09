package com.example.bookreview.ui.review

import android.content.RestrictionEntry.TYPE_NULL
import android.os.Bundle
import android.text.Editable
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreview.R
import kotlinx.android.synthetic.main.selected_review.*

class SelectedReviewActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.selected_review)

        var star = intent.extras?.getString("reviewStar")
        var content = intent.extras?.getString("reviewText")
        selected_review_star.rating = star?.toInt()!!.toFloat()
        selected_review_editText.text = Editable.Factory.getInstance().newEditable(content)
        selected_review_editText.inputType = TYPE_NULL

        /*if(현재 유저 == 리뷰 작성자){
            //현재 유저가 리뷰 작성자일 경우 수정 버튼, 삭제 버튼 보이도록 설정
            selected_review_modify_button.visibility = View.VISIBLE
            floatingDeleteButton.visibility = View.VISIBLE
        }*/

        selected_review_back_button.setOnClickListener {
            finish()
        }
    }
}
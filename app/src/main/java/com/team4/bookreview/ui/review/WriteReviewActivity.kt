package com.team4.bookreview.ui.review

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.team4.bookreview.R
import com.team4.bookreview.viewModel.ReviewViewModel
import kotlinx.android.synthetic.main.activity_write_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteReviewActivity : AppCompatActivity() {
    private val gallery = 1
    private val viewModel by viewModel<ReviewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)

        val id = intent.extras?.getString("id")
        val bookId = intent.extras?.getString("bookId")
        val title = intent.extras?.getString("title")

        write_review_book_title.text = title

        write_review_back_button.setOnClickListener {
            finish()
        }

        write_review_post_button.setOnClickListener {
            val review : Review = Review(
                "",
                id,
                write_review_edit_review.text.toString(),
                write_review_star.rating.toString(),
                "",
                bookId,
                "",
                "",
                ""
            )
            Log.d("Review Made" , review.toString())
            viewModel.writeReview(review){
                it.resultCode?.let { code ->
                    Log.d("ResultCode" , code.toString())
                }
            }
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        }

        write_review_edit_review.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count > write_review_editText.counterMaxLength) {
                    write_review_editText.error = "리뷰는 1000자 이내로 작성해 주세요!"
                } else {
                    write_review_editText.isErrorEnabled = false
                }
            }
        })

        review_image_button.setOnClickListener {
            openGallery()
        }

        this.window.apply {
            //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == gallery){
                var currentImageUrl : Uri? = data?.data

                try{
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageUrl)
                    review_image.setImageBitmap(bitmap)
                }catch(e:Exception){
                    e.printStackTrace()
                }
            }
        } else{
          Log.d("ActivityResult", "something wrong")
        }
    }

    private fun openGallery(){
        val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, gallery)
    }

}
package com.example.bookreview.ui.review

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreview.R
import com.example.bookreview.viewModel.ReviewViewModel
import kotlinx.android.synthetic.main.write_review.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WriteReviewActivity : AppCompatActivity() {
    private val gallery = 1
    private val viewModel by viewModel<ReviewViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_review)

        val id = intent.extras?.getString("id")
        val bookId = intent.extras?.getString("bookId")

        write_review_back_button.setOnClickListener {
            finish()
        }

        write_review_post_button.setOnClickListener {
            val review : Review = Review(
                "",
                id,
                write_review_editText.text.toString(),
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
            finish()
        }

        write_review_editText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input : Int = write_review_editText.length()
                editText_num.text = "$input / 1,000 글자 수"
            }
        })

        review_image_button.setOnClickListener {
            openGallery()
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
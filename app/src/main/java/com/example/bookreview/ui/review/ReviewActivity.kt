package com.example.bookreview.ui.review

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.bookreview.R
import com.example.bookreview.viewModel.ReviewViewModel
import kotlinx.android.synthetic.main.review_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewActivity : AppCompatActivity() {
    private val viewModel by viewModel<ReviewViewModel>()
    private lateinit var adapter: ReviewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.review_list)

        val id = intent.extras?.getString("id")
        val bookId = intent.extras?.getString("bookId")

        adapter = ReviewAdapter(viewModel, id!!)
        review_recyclerView.adapter = adapter

        viewModel.isReviewLoaded.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })
        viewModel.requestReviewByBook(bookId!!.toInt())

        viewModel.isReviewDeleteBtnClicked.observe(this, Observer {
            val builder = AlertDialog.Builder(this)
            if (viewModel.delReviewWriter?.toInt() == id.toInt()) {

                builder.setTitle("삭제 확인")
                builder.setMessage("정말 삭제 하시겠습니까?")
                builder.setPositiveButton("삭제") { dialog, which ->
                    val idx = viewModel.delID
                    viewModel.delMyReview(idx!!.toInt()) {
                        it.resultCode.let { code ->
                            if (code == 100) {
                                adapter.notifyItemRemoved(viewModel.delPosition!!)
                                Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(
                                    this,
                                    "리뷰를 삭제하는데 실패했습니다\n잠시 후 다시 시도해주세요",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }

                builder.setNegativeButton("취소") { dialog, which ->
                    Toast.makeText(this, "취소하였습니다", Toast.LENGTH_LONG).show()
                }


            } else {
                builder.setTitle("삭제 오류")
                builder.setMessage("자신이 작성한 리뷰만 삭제할 수 있습니다.")
                builder.setPositiveButton("확인") { dialog, which ->
                    Toast.makeText(this, "삭제할 수 없습니다.", Toast.LENGTH_LONG).show()
                }
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        })

        viewModel.isShareTextGenerated.observe(this, Observer {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, viewModel.shareText)
                type = "text/plain"
            }
            Log.e("In comment share : ", "after make sendIntent")
            val shareIntent = Intent.createChooser(sendIntent, null)
            Log.e("In comment share : ", "after make shareIntent")
            startActivity(shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        })

        write_review_button.setOnClickListener {
            startActivity(
                Intent(this, WriteReviewActivity::class.java)
                    .putExtra("id", id)
                    .putExtra("bookId", bookId)
            )
        }

        book_review_back_button.setOnClickListener {
            finish()
        }
        this.window.apply {
            //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }
    }
}

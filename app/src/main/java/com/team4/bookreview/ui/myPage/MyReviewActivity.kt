package com.team4.bookreview.ui.myPage

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.team4.bookreview.R
import com.team4.bookreview.utils.LoadingIndicator
import com.team4.bookreview.viewModel.ReviewViewModel
import kotlinx.android.synthetic.main.my_review_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyReviewActivity : AppCompatActivity(){
    private val viewModel by viewModel<ReviewViewModel>()
    private lateinit var adapter: MyReviewAdapter
    private var mLoadingIndicator: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_review_list)
        loadingIndicatorObserving()

        var id = intent.extras?.getString("id")
        var profileImage = intent.extras?.getString("profileImage")

        adapter = MyReviewAdapter(viewModel)
        my_review_recyclerView.adapter = adapter

        viewModel.requestMyReviews(id!!)
        viewModel.isAppReviewLoaded.observe(this, Observer {
            textView4.text = " ( ${viewModel.getMyReviewListSize()} )"
            adapter.notifyDataSetChanged()
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

        viewModel.isReviewDeleteBtnClicked.observe(this, Observer {
            val builder = AlertDialog.Builder(this)

            builder.setTitle("삭제 확인")
            builder.setMessage("정말 삭제 하시겠습니까?")
            builder.setPositiveButton("삭제") {dialog, which ->
                val idx = viewModel.delID
                viewModel.delMyReview(idx!!.toInt()){
                    it.resultCode.let { code ->
                        if (code == 100) {
                            adapter.notifyItemRemoved(viewModel.delPosition!!)
                            Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "리뷰를 삭제하는데 실패했습니다\n잠시 후 다시 시도해주세요", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            builder.setNegativeButton("취소") {dialog, which ->
                Toast.makeText(this, "취소하였습니다", Toast.LENGTH_LONG).show()
            }

            val dialog: AlertDialog = builder.create()

            dialog.show()
        })



        my_review_back_button.setOnClickListener {
            finish()
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
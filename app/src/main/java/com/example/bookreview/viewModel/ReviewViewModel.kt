package com.example.bookreview.viewModel

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

import com.example.bookreview.dto.ServerResponse
import com.example.bookreview.repository.ServerRepository
import com.example.bookreview.ui.myPage.MyReviewAdapter
import com.example.bookreview.ui.review.Review
import com.example.bookreview.utils.SingleLiveEvent

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.my_review_list.*
import java.util.concurrent.TimeUnit

class ReviewViewModel(private val serverRepository: ServerRepository): ViewModel() {
    private val _isReviewLoaded: SingleLiveEvent<Any> = SingleLiveEvent()
    private val _isShareTextGenerated: SingleLiveEvent<Any> = SingleLiveEvent()
    private val _isReviewDeleteBtnClicked: SingleLiveEvent<Any> = SingleLiveEvent()
    val isReviewLoaded: LiveData<Any>
        get() = _isReviewLoaded
    val isShareTextGenerated: LiveData<Any>
        get() = _isShareTextGenerated
    val isReviewDeleteBtnClicked: LiveData<Any>
        get() = _isReviewDeleteBtnClicked

    var shareText:String? = null
    var myReviewList = ArrayList<Review>()

    var delID: String? = null
    var delPosition: Int? = null
    var delReviewWriter: String? = null

    private val compositeDisposable = CompositeDisposable()
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun requestMyReviews(userId: String) {
        myReviewList.clear()
        apiCall(serverRepository.getMyReviewResponse(userId), onSuccess = Consumer {
            it.dataList?.let { list ->
                for (i in 0 until list.size()) {
                    val obj = list[i].asJsonObject
                    myReviewList.add(Review().jsonToObject(obj))
                }
                _isReviewLoaded.call()
            }
        },
        onError = Consumer {
            myReviewList.clear()
            Log.e("ERROR","ERROR : Post to Server ERROR")
        })
    }


    fun requestMyReviews(userId: String, success: (ServerResponse) -> Unit) {
        apiCall(serverRepository.getMyReviewResponse(userId), Consumer {
            success(it)
        })
    }

    fun delMyReview(idx: Int, success: (ServerResponse) -> Unit) {
        apiCall(serverRepository.delMyReviewResponse(idx), Consumer {
            myReviewList.removeAt(delPosition!!)
            success(it)
        })
    }

    fun delReview(idx: Int, success: (ServerResponse) -> Unit) {
        apiCall(serverRepository.delMyReviewResponse(idx), Consumer {
            success(it)
        })
    }

    fun requestAllReviews(success: (ServerResponse) -> Unit) {
        apiCall(serverRepository.getAllReviewResponse(), Consumer {
            success(it)
        })
    }

    fun requestReviewByBook(bookID: Int) {
        myReviewList.clear()
        apiCall(serverRepository.getBookReviewResponse(bookID), Consumer {
            it.dataList?.let { list ->
                for (i in 0 until list.size()) {
                    val obj = list[i].asJsonObject
                    myReviewList.add(Review().jsonToObject(obj))
                }
                _isReviewLoaded.call()
            }
        },
        onError = Consumer {
            myReviewList.clear()
            Log.e("ERROR","ERROR : Post to Server ERROR")
        })
    }

    fun requestAllReviews() {
        myReviewList.clear()
        apiCall(serverRepository.getAllReviewResponse(), onSuccess = Consumer {
            Log.e("data list in viewModel : ", it.dataList.toString())
            it.dataList?.let { list ->
                for (i in 0 until list.size()) {
                    val obj = list[i].asJsonObject
                    myReviewList.add(Review().jsonToObject(obj))
                }
                Log.e("data list in Review List : ", myReviewList.toString())
                _isReviewLoaded.call()
            }
        },
            onError = Consumer {
                myReviewList.clear()
                Log.e("ERROR","ERROR : Post to Server ERROR")
            })
    }

    fun writeReview(review : Review, success: (ServerResponse) -> Unit){
        apiCall(serverRepository.postMyReviewResponse(review), Consumer {
            success(it)
        })
    }

    fun getMyReviewListSize(): Int {
        return myReviewList.size
    }

    fun getMyReviewByPosition(position: Int): Review {
        return myReviewList[position]
    }

    fun generateShareText(position: Int) {
        val review = getMyReviewByPosition(position)
        var text = "제목 : ${review.bookName}\n"
        if (review.bookGenre != null) {
            text += "장르 : ${review.bookGenre}\n"
        }
        text += review.content
        text.replace("<b>", "")
        text.replace("</b>", "")
        shareText = text
        _isShareTextGenerated.call()
    }

    fun invokeDeleteButtonClick() {
        _isReviewDeleteBtnClicked.call()
    }


    fun <T> apiCall(single: Single<T>, onSuccess: Consumer<in T>,
                    onError: Consumer<in Throwable> = Consumer {
                        Log.e("오류발생",it.message!!)
                    },
                    indicator : Boolean = false, timeout: Long = 5){
                        addDisposable(single.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .timeout(timeout, TimeUnit.SECONDS)
//                            .doOnSubscribe{ if(indicator) startLoadingIndicator()  }
//                            .doAfterTerminate { stopLoadingIndicator() }
                            .subscribe(onSuccess, onError))
    }
}
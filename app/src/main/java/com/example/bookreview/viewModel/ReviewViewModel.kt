package com.example.bookreview.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel

import com.example.bookreview.dto.ServerResponse
import com.example.bookreview.repository.ServerRepository
import com.example.bookreview.ui.review.Review

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ReviewViewModel(private val serverRepository: ServerRepository): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun requestMyReviews(userId: String, success: (ServerResponse) -> Unit) {
        apiCall(serverRepository.getMyReviewResponse(userId), Consumer {
            success(it)
        })
    }

    fun requestAllReviews(success: (ServerResponse) -> Unit) {
        apiCall(serverRepository.getAllReviewResponse(), Consumer {
            success(it)
        })
    }
    fun writeReview(review : Review, success: (ServerResponse) -> Unit){
        apiCall(serverRepository.postMyReviewResponse(review), Consumer {
            success(it)
        })
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
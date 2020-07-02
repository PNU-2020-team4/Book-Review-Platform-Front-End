package com.team4.bookreview.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.team4.bookreview.dto.ServerResponse
import com.team4.bookreview.repository.ServerRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class HistoryViewModel(private val serverRepository: ServerRepository): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun addMyHistory(bookId : String?, bookTitle:String?, bookAuthor:String?, userID:String?,success: (ServerResponse) -> Unit) {
        apiCall(serverRepository.addHistory(bookId, bookTitle, bookAuthor, userID), Consumer {
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
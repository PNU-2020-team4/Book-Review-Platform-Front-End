package com.example.bookreview.viewModel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.bookreview.repository.NaverOAuthRepository
import com.example.bookreview.repository.ServerRepository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashViewModel(private val serverRepository: ServerRepository,
                      private val naverOAuthRepository: NaverOAuthRepository,
                      private val preferences: SharedPreferences
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun <T> apiCall(single: Single<T>, onSuccess: Consumer<in T>,
                    onError: Consumer<in Throwable> = Consumer {
                        Log.e("오류발생",it.message!!)
                    },
                    indicator : Boolean = false, timeout: Long = 5){
        addDisposable(single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(timeout, TimeUnit.SECONDS)
            .doOnSubscribe{ }
            .doAfterTerminate { }
            .subscribe(onSuccess, onError))
    }

    //refreshToken 검사
    fun validateRefreshToken(){

    }

    //accessToken 검사
    //accessToken이 없으면 loginActivity 실행

}
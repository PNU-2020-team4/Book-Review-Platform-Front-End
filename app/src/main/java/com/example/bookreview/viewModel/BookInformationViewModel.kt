package com.example.bookreview.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bookreview.repository.JsoupRepository
import com.example.bookreview.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.concurrent.TimeUnit

class BookInformationViewModel(private val jsoupRepository: JsoupRepository) : ViewModel(){
    private val compositeDisposable = CompositeDisposable()
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private val _isParsingFinished: SingleLiveEvent<Any> = SingleLiveEvent()
    val isParsingFinished: LiveData<Any>
        get() = _isParsingFinished
    private val _startLoadingIndicatorEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val startLoadingIndicatorEvent: LiveData<Any>
        get() = _startLoadingIndicatorEvent
    private val _stopLoadingIndicatorEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val stopLoadingIndicatorEvent: LiveData<Any>
        get() = _stopLoadingIndicatorEvent

    var title:String? = null
    var desc:String? = null
    var link:String? = null
    var imageSrc:String? = null
    var price:String? = null
    var author:String? = null
    var star:String? = null
    var reviewNUm:String? = null

    fun <T> apiCall(single: Single<T>, onSuccess: Consumer<in T>,
                    onError: Consumer<in Throwable> = Consumer {
                        Log.e("오류발생",it.message!!)
                    },
                    indicator : Boolean = false, timeout: Long = 5){
        addDisposable(single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(timeout, TimeUnit.SECONDS)
            .doOnSubscribe{ if(indicator) startLoadingIndicator()  }
            .doAfterTerminate { stopLoadingIndicator() }
            .subscribe(onSuccess, onError))
    }

    fun startLoadingIndicator(){
        _startLoadingIndicatorEvent.call()
    }

    fun stopLoadingIndicator(){
        _stopLoadingIndicatorEvent.call()
    }

    fun loadHtml(bid : String){
        apiCall(jsoupRepository.requestResponse(bid),
            Consumer {

                val doc: Document = Jsoup.parse(it)

                desc = doc.select("div[id=bookIntroContent] p").text()
                star = doc.select("a[id=txt_desc_point] strong").text().substringBefore("점 ")
                reviewNUm = doc.select("a[id=txt_desc_point] strong").text().substringAfter("점 ")
                _isParsingFinished.call()
            }
            ,onError = Consumer {
                Log.e("ERROR", "Parsing HTMl ERROR!")
            }
            ,indicator = true)
    }

}
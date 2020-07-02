package com.team4.bookreview.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.team4.bookreview.dto.Item
import com.team4.bookreview.repository.NaverBookSearchRepository
import com.team4.bookreview.utils.SingleLiveEvent
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchViewModel(private val naverBookSearchRepository: NaverBookSearchRepository) : ViewModel(){
    private val compositeDisposable = CompositeDisposable()
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private val _isBookSearchLoaded: SingleLiveEvent<Any> = SingleLiveEvent()
    val isBookSearchLoaded: LiveData<Any>
        get() = _isBookSearchLoaded

    private val _isBookClicked: SingleLiveEvent<Any> = SingleLiveEvent()
    val isBookClicked: LiveData<Any>
        get() = _isBookClicked

    var bookList: ArrayList<Item> = ArrayList()

    var link : String? = null
    var clickedBid : String? = null
    var imageUrl : String? = null
    var title : String? = null
    var author : String? = null
    var price : String? = null

    fun <T> apiCall(single: Single<T>, onSuccess: Consumer<in T>,
                    onError: Consumer<in Throwable> = Consumer {
                        Log.e("오류발생",it.message!!)
                    },
                    indicator : Boolean = false, timeout: Long = 5){
        addDisposable(single.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(timeout, TimeUnit.SECONDS)
//            .doOnSubscribe{ if(indicator)  }
//            .doAfterTerminate {  }
            .subscribe(onSuccess, onError))
    }

    fun invokeClick(){
        _isBookClicked.call()
    }

    fun getBookListSize() : Int {
        return bookList.size
    }

    fun getBookByPosition(position: Int) : Item{
        return bookList.get(position)
    }

    fun searchListClear(){
        bookList.clear()
    }

    fun bookSearch(title : String){
        apiCall(naverBookSearchRepository.getBookSearchResults(title,"10"),
        Consumer {
            searchListClear()
            if(it != null){
                for(i in it.items){
                    val imageSrc = i.image.replace("m1&udate","m140&udate")
                    bookList.add(Item(i.author, i.description, i.discount, imageSrc, i.isbn, i.link, i.price, i.pubdate, i.publisher, i.title))
                }
                _isBookSearchLoaded.call()
            }
        })
    }


}
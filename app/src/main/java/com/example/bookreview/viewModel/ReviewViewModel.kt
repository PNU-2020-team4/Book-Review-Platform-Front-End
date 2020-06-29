package com.example.bookreview.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bookreview.dto.BestSeller
import com.example.bookreview.dto.ReviewFromWeb
import com.example.bookreview.dto.ServerResponse
import com.example.bookreview.repository.JsoupRepository
import com.example.bookreview.repository.ServerRepository
import com.example.bookreview.ui.review.Review
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

class ReviewViewModel(private val serverRepository: ServerRepository,
                        private val jsoupRepository: JsoupRepository): ViewModel() {
    private val _isReviewLoaded: SingleLiveEvent<Any> = SingleLiveEvent()
    private val _isAppReviewLoaded: SingleLiveEvent<Any> = SingleLiveEvent()
    private val _isShareTextGenerated: SingleLiveEvent<Any> = SingleLiveEvent()
    private val _isReviewDeleteBtnClicked: SingleLiveEvent<Any> = SingleLiveEvent()
    val isReviewLoaded: LiveData<Any>
        get() = _isReviewLoaded
    val isAppReviewLoaded: LiveData<Any>
        get() = _isAppReviewLoaded
    val isShareTextGenerated: LiveData<Any>
        get() = _isShareTextGenerated
    val isReviewDeleteBtnClicked: LiveData<Any>
        get() = _isReviewDeleteBtnClicked
    private val _startLoadingIndicatorEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val startLoadingIndicatorEvent: LiveData<Any>
        get() = _startLoadingIndicatorEvent
    private val _stopLoadingIndicatorEvent: SingleLiveEvent<Any> = SingleLiveEvent()
    val stopLoadingIndicatorEvent: LiveData<Any>
        get() = _stopLoadingIndicatorEvent

    var shareText:String? = null
    var myReviewList = ArrayList<Review>()
    val webReviewList = ArrayList<ReviewFromWeb>()

    var delID: String? = null
    var delPosition: Int? = null
    var delReviewWriter: String? = null

    var bookRating : String? = null
    var reviewNum : String? = null


    private val compositeDisposable = CompositeDisposable()
    private fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun startLoadingIndicator(){
        _startLoadingIndicatorEvent.call()
    }

    fun stopLoadingIndicator(){
        _stopLoadingIndicatorEvent.call()
    }

    fun requestWebReviews(bid: String, page: String) {
        apiCall(jsoupRepository.requestReview(bid, page),
        Consumer {
            val doc: Document = Jsoup.parse(it)
            val elements: Elements = doc.select("ul[id=reviewList]").select("li")

            bookRating = doc.select("div[class=book_info_inner] div[class=txt_desc] a strong").text().split("점")[0]
            reviewNum = doc.select("div[class=book_info_inner] div[class=txt_desc] a").text().split("네티즌리뷰")[1]

            for(elem in elements){
                val ratingDate : Elements = elem.select("dl dd[class=txt_inline]")
                var date : String? = null
                var rating : String? = null
                val title = elem.select("dl dt").text()
                if(ratingDate.size == 2){
                    rating = ratingDate[0].text()
                    date = ratingDate[1].text()
                }
                else if(ratingDate.size == 1){
                    date = elem.select("dl dd[class=txt_inline]").text()
                }
                val url = elem.select("dl dt a").attr("href")
                val tempArr = elem.select("dl dd[id=review_author_${webReviewList.size+1}]").text().split(" : ")
                val user = tempArr[tempArr.lastIndex]
                val thumb = elem.select("div[class=thumb] a img").attr("src")
                val text = elem.select("dl dd[id=review_text_${webReviewList.size+1}]").text()
                webReviewList.add(ReviewFromWeb(user, title, text , thumb, url, rating, date!!))
            }
            _isReviewLoaded.call()
        }
        ,onError = Consumer {
            Log.e("ERROR", "Parsing HTMl ERROR!")
        }
        ,indicator = true)
    }

    fun requestMyReviews(userId: String) {
        myReviewList.clear()
        apiCall(serverRepository.getMyReviewResponse(userId),
            onSuccess = Consumer {
            it.dataList?.let { list ->
                for (i in 0 until list.size()) {
                    val obj = list[i].asJsonObject
                    myReviewList.add(Review().jsonToObject(obj))
                }
                _isAppReviewLoaded.call()
            }
        },
        onError = Consumer {
            myReviewList.clear()
            Log.e("ERROR","ERROR : Post to Server ERROR")
        },
        indicator = true)
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

    fun getWebReviewListSize(): Int {
        return webReviewList.size
    }

    fun getWebReviewByPosition(position: Int) : ReviewFromWeb{
        return webReviewList[position]
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
            .doOnSubscribe{ if(indicator) startLoadingIndicator()  }
            .doAfterTerminate { stopLoadingIndicator() }
            .subscribe(onSuccess, onError))
    }
}
package com.team4.bookreview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.team4.bookreview.R
import com.team4.bookreview.book.Book
import kotlinx.android.synthetic.main.new_book_fragment.*

class NewBookFragment : Fragment() {
    private val bookList = arrayListOf(
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        ),
        Book(
            "the having",
            "novel",
            "Park",
            "한빛",
            15000,
            "userbutton",
            "소설",
            "20200508",
            "ABCD"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.new_book_fragment, container)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        new_book_recyclerView.apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = BookAdapter(bookList, context)
        }
    }

    companion object {
        fun create(): NewBookFragment =
            NewBookFragment()
    }
}
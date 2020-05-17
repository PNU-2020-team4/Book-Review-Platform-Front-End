package com.example.bookreview.fragment

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R
import com.example.bookreview.book.Book
import com.example.bookreview.intent.BookActivity

class BookAdapter(private val bookList: ArrayList<Book>, private val context: Context) : RecyclerView.Adapter<BookAdapter.BookViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BookViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book : Book = bookList[position]
        holder.bind(book)

        holder.itemView.setOnClickListener {
            val nextIntent = Intent(context, BookActivity::class.java)
            nextIntent.putExtra("bookPhoto", book.image)
            nextIntent.putExtra("bookTitle", book.title)
            nextIntent.putExtra("bookAuthor", book.author)
            nextIntent.putExtra("bookPrice", book.price)
            context.startActivity(nextIntent)
        }
    }

    override fun getItemCount(): Int = bookList.size

    class BookViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder(inflater.inflate(R.layout.book_list, parent, false)){
        private var bookPhoto : ImageView? = null
        private var bookTitle : TextView? = null
        private var bookAuthor : TextView? = null
        private var bookPrice :TextView? = null

        init{
            bookPhoto = itemView.findViewById(R.id.book_photo_Img)
            bookTitle = itemView.findViewById(R.id.book_title)
            bookAuthor = itemView.findViewById(R.id.book_author)
            bookPrice = itemView.findViewById(R.id.book_price)
        }

        fun bind(book: Book){
            bookTitle?.text = book.title
            bookAuthor?.text = book.author
            bookPrice?.text = book.price.toString()
        }
    }
}
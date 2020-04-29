package com.example.bookreview.viewPager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.EventViewHolder>() {
    private val eventList = listOf(R.layout.fragment_1, R.layout.fragment_2, R.layout.fragment_3, R.layout.fragment_4)
    private var pos : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventViewHolder(
            LayoutInflater.from(parent.context).inflate(
                eventList[pos++],
                parent,
                false
            )
        )



    override fun getItemCount() = eventList.count()

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        (holder.view as? TextView)?.also{

        }
    }

    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
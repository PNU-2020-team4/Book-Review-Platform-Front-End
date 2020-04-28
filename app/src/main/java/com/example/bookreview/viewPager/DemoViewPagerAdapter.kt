package com.example.bookreview.viewPager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreview.R

class DemoViewPagerAdapter : RecyclerView.Adapter<DemoViewPagerAdapter.EventViewHolder>() {
    val eventList = listOf(R.layout.fragment_1, R.layout.fragment_2, R.layout.fragment_3, R.layout.fragment_4)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EventViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_1,
                parent,
                false
            )
        )

    override fun getItemCount() = eventList.count()
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        (holder.view as? TextView)?.also{
            //it.text = "Page " + eventList.get(position)
        }
    }

    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}
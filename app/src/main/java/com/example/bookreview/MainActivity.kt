package com.example.bookreview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookreview.fragment.ResourceStore
import com.example.bookreview.ui.myPage.MyPageActivity
import com.example.bookreview.ui.search.SearchActivity
import com.example.bookreview.viewModel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.book.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var adapter : MainBestSellerAdapter
    private var userId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewPager()
        setTab()

        adapter = MainBestSellerAdapter(viewModel)
        main_popular_recycler.adapter = adapter

        val profileImage = intent.extras?.getString("profileImage")
        val id = intent.extras?.getString("id")
        Log.e("On create Main , User profile : ", profileImage)
        Log.e("On create Main, User id : ", id)
        //status bar 투명하게 처리
        this.window.apply {
            //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }

        main_search.setOnClickListener {
            val nextIntent = Intent(this, SearchActivity::class.java)
            nextIntent.putExtra("ID", id)
            startActivity(nextIntent)
        }

        main_user_button.setOnClickListener {
            val nextIntent = Intent(this, MyPageActivity::class.java).putExtra("profileImage",profileImage)
            nextIntent.putExtra("id", id)
            startActivity(nextIntent)
        }

        Picasso.get().load(profileImage).into(main_user_button)

        viewModel.loadBestSeller("16329560")
        viewModel.isLoadBestSellerFinished.observe(this, Observer {
            Picasso.get().load(viewModel.bestImage).into(main_best_seller_image)
            main_best_seller_author.text = viewModel.bestAuthor
            main_best_seller_title.text = viewModel.bestTitle
            main_best_seller_star.rating = (viewModel.bestStar!!.toFloat()/2.0).toFloat()
            main_best_seller_review_number.text = viewModel.bestReviews
        })

        viewModel.loadPopularList()
        viewModel.isLoadPopularListFinished.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setViewPager() {
        viewpager.adapter = object : FragmentStateAdapter(this) {

            override fun createFragment(position: Int): Fragment {
                return ResourceStore.pagerFragments[position]
            }

            override fun getItemCount(): Int {
                return ResourceStore.tabList.size
            }
        }
    }

    private fun setTab() {
        TabLayoutMediator(tab_layout, viewpager) { tab : TabLayout.Tab, position ->
            tab.text = ResourceStore.tabList[position]
            main_title.setOnClickListener(){
                viewpager.setCurrentItem(TabLayout.Tab.INVALID_POSITION, true)
            }
        }.attach()
    }
}

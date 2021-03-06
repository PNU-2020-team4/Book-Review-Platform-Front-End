package com.team4.bookreview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.api.load
import com.team4.bookreview.ui.book.BookInformationActivity
import com.team4.bookreview.ui.myPage.MyPageActivity
import com.team4.bookreview.ui.search.SearchActivity
import com.team4.bookreview.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var adapter : MainBestSellerAdapter
    private var userId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val profileImage = intent.extras?.getString("profileImage")
        val id = intent.extras?.getString("id")
        val email = intent.extras?.getString("mail")
        val name = intent.extras?.getString("nickname")

        adapter = MainBestSellerAdapter(viewModel, this, id!!)
        main_popular_recycler.adapter = adapter

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
            val nextIntent = Intent(this, MyPageActivity::class.java)
                .putExtra("profileImage",profileImage)
                .putExtra("id", id)
                .putExtra("mail", email)
                .putExtra("nickname", name)
            startActivity(nextIntent)
        }

        //TODO : CURATION
        main_best_seller_image.setOnClickListener {
            val nextIntent = Intent(this, BookInformationActivity::class.java)
                .putExtra("uid", id)
                .putExtra("bid", "16329560")
                .putExtra("link", viewModel.bestLink)
            startActivity(nextIntent)
        }

        //Picasso.get().load(profileImage).into(main_user_button)
        main_user_button.load(profileImage)

        //TODO : CURATION
        viewModel.loadBestSeller("16329560")
        viewModel.isLoadBestSellerFinished.observe(this, Observer {
            //Picasso.get().load(viewModel.bestImage).into(main_best_seller_image)
            main_best_seller_image.load(viewModel.bestImage)
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

//    private fun setViewPager() {
//        viewpager.adapter = object : FragmentStateAdapter(this) {
//
//            override fun createFragment(position: Int): Fragment {
//                return ResourceStore.pagerFragments[position]
//            }
//
//            override fun getItemCount(): Int {
//                return ResourceStore.tabList.size
//            }
//        }
//    }
//
//    private fun setTab() {
//        TabLayoutMediator(tab_layout, viewpager) { tab : TabLayout.Tab, position ->
//            tab.text = ResourceStore.tabList[position]
//            main_title.setOnClickListener(){
//                viewpager.setCurrentItem(TabLayout.Tab.INVALID_POSITION, true)
//            }
//        }.attach()
//    }
}

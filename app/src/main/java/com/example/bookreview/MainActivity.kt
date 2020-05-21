package com.example.bookreview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookreview.fragment.ResourceStore
import com.example.bookreview.ui.myPage.MyPageActivity
import com.example.bookreview.ui.search.SearchActivity
import com.example.bookreview.viewModel.MainViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setViewPager()
        setTab()

        val profileImage = intent.extras?.getString("profileImage")
        val userId = intent.extras?.getString("userId")
        //status bar 투명하게 처리
        this.window.apply {
            //clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }

        main_search.setOnClickListener {
            val nextIntent = Intent(this, SearchActivity::class.java)
            startActivity(nextIntent)
        }

        main_user_button.setOnClickListener {
            val nextIntent = Intent(this, MyPageActivity::class.java)
                .putExtra("profileImage",profileImage)
                .putExtra("userId", userId)
            startActivity(nextIntent)
        }

        Picasso.get().load(profileImage).into(main_user_button)
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

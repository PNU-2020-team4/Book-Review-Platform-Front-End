package com.example.bookreview

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.bookreview.viewModel.MainViewModel
import com.nhn.android.naverlogin.OAuthLogin
import com.example.bookreview.viewPager.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private val tabTitles = listOf("홈", "랭킹", "신간 도서", "MY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //viewModel.testLoad()
        viewpager.adapter = ViewPagerAdapter()

        TabLayoutMediator(tab_layout, viewpager) { tab : TabLayout.Tab, position : Int ->
            tab.text = tabTitles[position]
            viewpager.setCurrentItem(tab.position, true)
            main_title.setOnClickListener(){
                viewpager.setCurrentItem(TabLayout.Tab.INVALID_POSITION, true)
            }
        }.attach()

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
            startActivity(nextIntent)
        }
    }

    override fun onBackPressed() {
        finish()
    }
}

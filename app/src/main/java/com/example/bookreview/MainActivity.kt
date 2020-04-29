package com.example.bookreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bookreview.viewModel.MainViewModel
import com.nhn.android.naverlogin.OAuthLogin
import com.example.bookreview.viewPager.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<MainViewModel>()
    private val tabTitles = listOf("홈", "랭킹", "신간 도서", "소식")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //viewModel.testLoad()
        viewModel.testLogin(this)
        viewpager.adapter = ViewPagerAdapter()

        TabLayoutMediator(tab_layout, viewpager) { tab : TabLayout.Tab, position : Int ->
            tab.text = tabTitles[position]
            viewpager.setCurrentItem(tab.position, true)
        }.attach()
    }
}

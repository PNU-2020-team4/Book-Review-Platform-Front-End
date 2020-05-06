package com.example.bookreview

import android.content.Intent
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
    private val tabTitles = listOf("홈", "랭킹", "신간 도서", "MY")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //viewModel.testLoad()
        viewpager.adapter = ViewPagerAdapter()

        TabLayoutMediator(tab_layout, viewpager) { tab : TabLayout.Tab, position : Int ->
            tab.text = tabTitles[position]
            viewpager.setCurrentItem(tab.position, true)
            app_name.setOnClickListener(){
                viewpager.setCurrentItem(TabLayout.Tab.INVALID_POSITION, true)
            }
        }.attach()

        searchForm.setOnClickListener {
            val nextIntent = Intent(this, SearchActivity::class.java)
            startActivity(nextIntent)
        }

        user_button.setOnClickListener {
            val nextIntent = Intent(this, MyPageActivity::class.java)
            startActivity(nextIntent)
        }
    }
}

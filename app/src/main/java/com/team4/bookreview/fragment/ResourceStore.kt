package com.team4.bookreview.fragment

interface ResourceStore {
    companion object {
        val tabList = listOf(
            "홈", "베스트셀러", "신간 도서", "MY"
        )
        val pagerFragments = listOf(
            HomeFragment.create(), BestSellerFragment.create(),
           NewBookFragment.create(), MyFragment.create())
    }
}
package com.example.bookreview.di

import com.example.bookreview.viewModel.BookInformationViewModel
import com.example.bookreview.viewModel.MainViewModel
import com.example.bookreview.viewModel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        MainViewModel(get(),get(),get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        BookInformationViewModel(get())
    }
}
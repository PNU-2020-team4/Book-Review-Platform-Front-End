package com.example.bookreview.di

import com.example.bookreview.viewModel.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel{
        MainViewModel(get(),get(),get(),get(), get())
    }
    viewModel {
        SearchViewModel(get())
    }
    viewModel {
        BookInformationViewModel(get())
    }
    viewModel {
        ReviewViewModel(get())
    }
    viewModel {
        HistoryViewModel(get())
    }
    viewModel {
        SplashViewModel(get(), get(), get(), get())
    }
    viewModel {
        MypageViewModel(get())
    }
}
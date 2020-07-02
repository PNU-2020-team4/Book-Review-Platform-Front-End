package com.team4.bookreview.di

import com.team4.bookreview.viewModel.*
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
        ReviewViewModel(get(), get())
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
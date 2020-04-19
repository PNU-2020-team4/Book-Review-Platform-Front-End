package com.example.bookreview

import android.app.Application

class Application : Application() {
    //private val DiModule =listOf(apiModule, viewModelModule)

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidContext(this@MyRealTripChallengeApplication)
//            modules(DiModule)
//        }
    }
}
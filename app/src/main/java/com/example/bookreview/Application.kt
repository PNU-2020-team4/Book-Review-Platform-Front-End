package com.example.bookreview

import android.app.Application
import com.example.bookreview.di.serverModule
import com.example.bookreview.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    private val DiModule = listOf(serverModule, viewModelModule)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(DiModule)
        }
    }
}
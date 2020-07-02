package com.team4.bookreview

import android.app.Application
import com.team4.bookreview.di.serverModule
import com.team4.bookreview.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {
    private val DiModule = listOf(serverModule, viewModelModule)
//    private val BOOK_SHARED_PREFERENCES_NAME = "book_shared_preferences"
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(DiModule)
        }
//        var pref = getSharedPreferences(BOOK_SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
//        if(pref.getString("ACCESS_TOKEN",null) != null) Log.e("엑세스토큰",pref.getString("ACCESS_TOKEN",null))
    }
}
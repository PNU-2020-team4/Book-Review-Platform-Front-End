package com.example.bookreview.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.bookreview.api.RefreshService
import com.example.bookreview.api.ServerService
import com.example.bookreview.api.UserService
import com.example.bookreview.repository.NaverOAuthRepository
import com.example.bookreview.repository.NaverOAuthRepositoryImpl
import com.example.bookreview.repository.ServerRepository
import com.example.bookreview.repository.ServerRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val serverModule = module {
    factory { provideOkHttpClient()}
    factory { provideServerApi(provideServerRetrofit(get())) }
    factory { provideUserApi(provideUserRetrofit(get())) }
    factory { provideRefreshApi((provideRefreshTokenRetrofit(get()))) }

    single<ServerRepository> { ServerRepositoryImpl(get()) }
    single<NaverOAuthRepository> { NaverOAuthRepositoryImpl(get(), get(), get(), get()) }

    single{ provideSettingsPreferences(androidApplication())}
}

fun provideServerApi(retrofit: Retrofit):ServerService = retrofit.create(ServerService::class.java)
fun provideUserApi(retrofit: Retrofit):UserService = retrofit.create(UserService::class.java)
fun provideRefreshApi(retrofit: Retrofit):RefreshService = retrofit.create(RefreshService::class.java)


fun provideServerRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://pnubookreview.herokuapp.com").client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).build()
}

fun provideUserRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://openapi.naver.com").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).build()
}

fun provideRefreshTokenRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://nid.naver.com").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient().newBuilder()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    httpClientBuilder.addInterceptor(logging)

    return httpClientBuilder.build()
}

private const val PREFERENCES_FILE_KEY = "book_shared_preferences"

private fun provideSettingsPreferences(app: Application): SharedPreferences =
    app.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
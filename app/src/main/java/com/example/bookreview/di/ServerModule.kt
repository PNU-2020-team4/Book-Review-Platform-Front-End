package com.example.bookreview.di

import com.example.bookreview.api.ServerService
import com.example.bookreview.repository.ServerRepository
import com.example.bookreview.repository.ServerRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val serverModule = module {
    factory { provideOkHttpClient()}
    factory { provideServerApi(provideServerRetrofit(get())) }

    single<ServerRepository> { ServerRepositoryImpl(get()) }
}

fun provideServerApi(retrofit: Retrofit):ServerService = retrofit.create(ServerService::class.java)

fun provideServerRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl("https://pnubookreview.herokuapp.com").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(
            RxJava2CallAdapterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    val httpClientBuilder = OkHttpClient().newBuilder()
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    httpClientBuilder.addInterceptor(logging)

    return httpClientBuilder.build()
}
package com.athulvs987.myapplication.api

import com.athulvs987.myapplication.utils.Constance
import okhttp3.logging.HttpLoggingInterceptor

import okhttp3.OkHttpClient
import retrofit.GsonConverterFactory
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create

class  RetrofitInstance {
    companion object {
        private val retofit by lazy {
            val logingInterceptor =HttpLoggingInterceptor()
            logingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client =OkHttpClient.Builder()
                .addInterceptor(logingInterceptor)
                .build()
            Retrofit.Builder()
                .baseUrl(Constance.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()as Converter.Factory)
                .client(client)
                .build()

        }
        val newsApi by lazy {
            retofit.create(NewsAPI::class.java)
        }
    }
}






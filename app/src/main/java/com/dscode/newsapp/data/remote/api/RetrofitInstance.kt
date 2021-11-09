package com.dscode.newsapp.data.remote

import com.dscode.newsapp.common.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val newsApi: NewsApi by lazy {
        retrofit.create(newsApi::class.java)
    }
}
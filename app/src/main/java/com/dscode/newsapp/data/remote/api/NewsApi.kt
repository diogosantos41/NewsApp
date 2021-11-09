package com.dscode.newsapp.data.remote

import retrofit2.http.GET

interface NewsApi {
    suspend fun getNews(): Any
}
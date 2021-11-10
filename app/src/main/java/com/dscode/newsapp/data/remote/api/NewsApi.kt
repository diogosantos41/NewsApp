package com.dscode.newsapp.data.remote.api

import com.dscode.newsapp.common.Constants.API_KEY
import com.dscode.newsapp.data.remote.model.NewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        countryCode: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsDto

    @GET("/v2/top-headlines")
    suspend fun getNewsByCategory(
        @Query("country")
        countryCode: String,
        @Query("category")
        category: String,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsDto
}
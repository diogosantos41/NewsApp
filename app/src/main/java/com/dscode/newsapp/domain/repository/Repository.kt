package com.dscode.newsapp.domain.repository

import com.dscode.newsapp.common.Resource
import com.dscode.newsapp.domain.model.News


interface Repository {

    suspend fun getNews(countryCode: String): Resource<News>

    suspend fun getNewsByCategory(countryCode: String, category: String): Resource<News>

}
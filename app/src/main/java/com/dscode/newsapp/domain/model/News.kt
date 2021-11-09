package com.dscode.newsapp.domain.model

import com.dscode.newsapp.data.remote.model.Article

data class News(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)
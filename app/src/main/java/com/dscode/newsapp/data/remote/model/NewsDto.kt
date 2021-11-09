package com.dscode.newsapp.data.remote.model

import com.dscode.newsapp.domain.model.News

data class NewsDto(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)

fun NewsDto.toNews(): News {
    return News(
        articles = articles,
        status = status,
        totalResults = totalResults,
    )
}
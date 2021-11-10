package com.dscode.newsapp.data.repository

import com.dscode.newsapp.common.Failure
import com.dscode.newsapp.common.Resource
import com.dscode.newsapp.data.remote.api.RetrofitInstance
import com.dscode.newsapp.data.remote.model.toNews
import com.dscode.newsapp.domain.model.News
import com.dscode.newsapp.domain.repository.Repository
import retrofit2.HttpException
import java.io.IOException

class RepositoryImpl : Repository {

    override suspend fun getNews(countryCode: String): Resource<News> {
        return try {
            val news = RetrofitInstance.newsApi.getNews(countryCode).toNews()
            Resource.Success<News>(news)
        } catch (e: HttpException) {
            Resource.Error<News>(Failure.UnexpectedError)
        } catch (e: IOException) {
            Resource.Error<News>(Failure.NetworkConnection)
        }
    }

    override suspend fun getNewsByCategory(countryCode: String, category: String): Resource<News> {
        return try {
            val news = RetrofitInstance.newsApi.getNewsByCategory(countryCode, category).toNews()
            Resource.Success<News>(news)
        } catch (e: HttpException) {
            Resource.Error<News>(Failure.UnexpectedError)
        } catch (e: IOException) {
            Resource.Error<News>(Failure.NetworkConnection)
        }
    }
}
package com.dscode.newsapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscode.newsapp.common.Failure
import com.dscode.newsapp.common.Resource
import com.dscode.newsapp.data.remote.model.Article
import com.dscode.newsapp.data.repository.RepositoryImpl
import com.dscode.newsapp.domain.model.News
import kotlinx.coroutines.launch

class MainViewModel(private val repositoryImpl: RepositoryImpl) : ViewModel() {

    private val _news: MutableLiveData<News> = MutableLiveData()
    private lateinit var newsResponse: Resource<News>

    private val _newsFailure: MutableLiveData<Failure> = MutableLiveData()

    private val _selectedArticle: MutableLiveData<Article> = MutableLiveData()

    fun callGetNews(countryCode: String) {
        viewModelScope.launch {
            newsResponse = repositoryImpl.getNews(countryCode)
        }.invokeOnCompletion {
            handleNewsResponse()
        }
    }

    private fun handleNewsResponse() {
        when (newsResponse) {
            is Resource.Success -> {
                if (newsResponse.data == null || newsResponse.data?.articles.isNullOrEmpty()) {
                    _newsFailure.value = Failure.ListIsEmpty
                } else {
                    _news.value = newsResponse.data
                }
            }
            is Resource.Error -> {
                _newsFailure.value = newsResponse.failure
            }
        }
    }

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }

    fun getSelectedArticle() = _selectedArticle.value

    fun getNews(): LiveData<News> = _news

    fun onFailure(): LiveData<Failure> = _newsFailure

    fun onSelectedArticleChange(): LiveData<Article> = _selectedArticle

}
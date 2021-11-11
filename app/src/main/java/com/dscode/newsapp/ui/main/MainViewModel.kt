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

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()

    private val _selectedArticle: MutableLiveData<Article> = MutableLiveData()

    private val _countryName: MutableLiveData<String> = MutableLiveData()
    val countryName = _countryName

    private var countryCode: String = ""

    private val _searchQuery: MutableLiveData<String> = MutableLiveData()
    private var searchQuery: String = ""

    fun callGetNews() {
        if (searchQuery.isNullOrEmpty()) {
            callGetNews(countryCode)
        } else {
            callGetNewsByCategory(countryCode, searchQuery)
        }
    }

    private fun callGetNews(countryCode: String) {
        _isLoading.value = true
        viewModelScope.launch {
            newsResponse = repositoryImpl.getNews(countryCode)
        }.invokeOnCompletion {
            handleNewsResponse()
        }
    }

    private fun callGetNewsByCategory(countryCode: String, queryText: String) {
        _isLoading.value = true
        viewModelScope.launch {
            newsResponse = repositoryImpl.getNewsByCategory(countryCode, queryText)
        }.invokeOnCompletion {
            handleNewsResponse()
        }
    }

    private fun handleNewsResponse() {
        when (newsResponse) {
            is Resource.Success -> {
                setSearchQuery()
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
        _isLoading.value = false
    }

    fun updateCountry(countryCode: String, countryName: String) {
        if (countryCode != this.countryCode) {
            this.countryCode = countryCode
            _countryName.value = countryName
            callGetNews()
        }
    }

    fun updateSearchQuery(queryText: String) {
        searchQuery = queryText
        if (searchQuery.isNullOrEmpty()) {
            callGetNews()
        } else {
            callGetNewsByCategory(countryCode, queryText)
        }
    }

    fun clearQuery() {
        updateSearchQuery("")
    }

    private fun setSearchQuery() {
        _searchQuery.value = searchQuery
    }
    
    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }

    fun getSelectedArticle() = _selectedArticle.value

    fun getNews(): LiveData<News> = _news

    fun onFailure(): LiveData<Failure> = _newsFailure

    fun onSelectedArticleChange(): LiveData<Article> = _selectedArticle

    fun getCountryName(): LiveData<String> = _countryName

    fun isLoading(): LiveData<Boolean> = _isLoading

    fun onCategoryQueryChange(): LiveData<String> = _searchQuery
}
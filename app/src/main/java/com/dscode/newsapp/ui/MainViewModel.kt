package com.dscode.newsapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dscode.newsapp.common.Failure
import com.dscode.newsapp.common.Failure.ListIsEmpty
import com.dscode.newsapp.common.Resource
import com.dscode.newsapp.data.repository.RepositoryImpl
import com.dscode.newsapp.domain.model.News
import kotlinx.coroutines.launch

class MainViewModel(private val repositoryImpl: RepositoryImpl) : ViewModel() {

    private val _news: MutableLiveData<News> = MutableLiveData()
    private lateinit var newsResponse: Resource<News>

    private val _newsFailure: MutableLiveData<Failure> = MutableLiveData()

    fun callGetNews() {
        viewModelScope.launch {
            newsResponse = repositoryImpl.getNews("us")
        }.invokeOnCompletion {
            handleNewsResponse()
        }
    }

    private fun handleNewsResponse() {
        when (newsResponse) {
            is Resource.Success -> {
                if (newsResponse.data == null || newsResponse.data?.articles.isNullOrEmpty()) {
                    _newsFailure.value = ListIsEmpty
                } else {
                    _news.value = newsResponse.data
                }
            }
            is Resource.Error -> {
                _newsFailure
            }
        }
    }

    fun getNews(): LiveData<News> = _news

    fun onFailure(): LiveData<Failure> = _newsFailure

}
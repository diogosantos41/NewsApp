package com.dscode.newsapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dscode.newsapp.data.repository.RepositoryImpl

class MainViewModelFactory(private val repositoryImpl: RepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repositoryImpl) as T
    }
}
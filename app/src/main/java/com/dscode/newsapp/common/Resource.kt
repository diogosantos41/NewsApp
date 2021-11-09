package com.dscode.newsapp.common

sealed class Resource<T>(val data: T? = null, val failure: Failure? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(failure: Failure, data: T? = null) : Resource<T>(data, failure)

}
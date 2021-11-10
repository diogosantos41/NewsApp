package com.dscode.newsapp.common

sealed class Failure {

    // Generic Failure
    object NetworkConnection : Failure()
    object UnexpectedError : Failure()

    // News Failure
    object ListIsEmpty : Failure()

}
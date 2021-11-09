package com.dscode.newsapp.common

sealed class Failure {
    object NetworkConnection : Failure()
    object UnexpectedError : Failure()
    abstract class FeatureFailure : Failure()
}
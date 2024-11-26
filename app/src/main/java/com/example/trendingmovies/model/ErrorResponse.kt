package com.example.trendingmovies.model

data class ErrorResponse(
    val errorCode: Int = 0,
    val errorMessage: String? = null
)
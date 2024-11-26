package com.example.trendingmovies.service

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * add api key to requesta
 */
class AuthInterceptor(private val apiKey: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val initialRequest = chain.request()
        val httpUrl = initialRequest.url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        val requestBuilder: Request.Builder = initialRequest.newBuilder()
            .url(httpUrl)

        return chain.proceed(requestBuilder.build())
    }
}
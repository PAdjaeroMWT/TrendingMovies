package com.example.trendingmovies.utils

import com.example.trendingmovies.model.ErrorResponse
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

object ErrorUtil {
    fun parseError(response: Response<*>, retrofit: Retrofit): ErrorResponse? {
        val parser =
            retrofit.responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, arrayOfNulls(0))
        return try {
            return parser.convert(response.errorBody()!!)
        } catch (e: IOException) {
            ErrorResponse()
        }
    }
}
package com.example.trendingmovies.repository.remote

import android.util.Log
import com.example.trendingmovies.model.MovieDescription
import com.example.trendingmovies.model.ServerResult
import com.example.trendingmovies.model.TrendingMovies
import com.example.trendingmovies.service.RetrofitService
import com.example.trendingmovies.utils.ErrorUtil
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * class to interact with remote api
 */
class MovieRemote @Inject constructor (private val retrofit: Retrofit) {

    suspend fun getTrendingMoviesList(): ServerResult<TrendingMovies>{
        val tmdbService = retrofit.create(RetrofitService::class.java)
        Log.d("Movie Remote", "fetching trending movies")
        return getResponse(
            request = {tmdbService.getPopularMovies()},
            errorMessage = "Error fetching Trending Movies List"
        )
    }

    suspend fun getMovie(id: Int): ServerResult<MovieDescription>{
        val tmdbService = retrofit.create(RetrofitService::class.java)
        return getResponse(
            request = {tmdbService.getMovie(id)},
            errorMessage = "Error fetching Movie Description"
        )
    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>, errorMessage: String): ServerResult<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return ServerResult.success(result.body())
            } else {
                val errorResponse = ErrorUtil.parseError(result, retrofit)
                ServerResult.error(errorResponse?.errorMessage ?: errorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            ServerResult.error("Server error", null)
        }
    }
}
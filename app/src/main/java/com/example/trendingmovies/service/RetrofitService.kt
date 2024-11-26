package com.example.trendingmovies.service

import com.example.trendingmovies.model.MovieDescription
import com.example.trendingmovies.model.TrendingMovies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * interface for retrofit api calls
 */
interface RetrofitService {

    @Headers("Accept: application/json")
    @GET("/3/trending/movie/week")
    suspend fun getPopularMovies() : Response<TrendingMovies>

    @Headers("Accept: application/json")
    @GET("/3/movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") id: Int) : Response<MovieDescription>
}
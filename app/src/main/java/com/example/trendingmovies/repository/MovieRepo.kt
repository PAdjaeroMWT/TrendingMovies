package com.example.trendingmovies.repository

import android.util.Log
import com.example.trendingmovies.model.MovieDescription
import com.example.trendingmovies.model.ServerResult
import com.example.trendingmovies.model.TrendingMovies
import com.example.trendingmovies.repository.cache.MovieDao
import com.example.trendingmovies.repository.remote.MovieRemote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * repository to fetch data from remote source or room db
 */
class MovieRepo @Inject constructor(
    private val movieRemote: MovieRemote,
    private val movieDao: MovieDao
) {
    private val TAG = "Movie Repo"
    fun getMovie(id: Int): Flow<ServerResult<MovieDescription>> {
        Log.d(TAG, "getting movie from repo")
        return flow {
            emit(ServerResult.loading())
            emit(movieRemote.getMovie(id))
        }.flowOn(Dispatchers.IO)
    }

    private fun getCachedMovies(): ServerResult<TrendingMovies>? = movieDao.selectAll()?.let {
        ServerResult.success(TrendingMovies(1,it,1,20))
    }

    fun getTrendingMovies(): Flow<ServerResult<TrendingMovies>?> {
        Log.d(TAG, "getting trending movies")
        return flow {
            emit(getCachedMovies())
            emit(ServerResult.loading())
            val result = movieRemote.getTrendingMoviesList()

            if (result.status == ServerResult.Status.SUCCESS) {
                result.data?.results?.let { it ->
                    Log.d(TAG, "result received")
                    Log.d(TAG, result.data.results.toString())
                    movieDao.updateAll(it)
                    movieDao.insertAll(it)
                }
            }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
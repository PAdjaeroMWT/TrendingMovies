package com.example.trendingmovies.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.model.ServerResult
import com.example.trendingmovies.model.TrendingMovies
import com.example.trendingmovies.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(private val movieRepo: MovieRepo) : ViewModel() {

    private val TAG = MoviesListViewModel::class.java.simpleName
    private val _movieList = MutableLiveData<ServerResult<TrendingMovies>>()
    val movieList = _movieList

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        Log.d(TAG, "view model call to get list of movies")
        viewModelScope.launch {
            movieRepo.getTrendingMovies().collect {
                _movieList.value = it
            }
        }
        Log.d(TAG, "view model call to get list of movies complete")
    }
}
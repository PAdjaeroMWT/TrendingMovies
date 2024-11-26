package com.example.trendingmovies.viewmodel

import androidx.lifecycle.*
import com.example.trendingmovies.model.MovieDescription
import com.example.trendingmovies.model.ServerResult
import com.example.trendingmovies.repository.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieRepo: MovieRepo) : ViewModel() {

    private var _id = MutableLiveData<Int>()
    private val _movie: LiveData<ServerResult<MovieDescription>> =
        _id.distinctUntilChanged().switchMap {
            liveData {
                movieRepo.getMovie(it).onStart {
                    emit(ServerResult.loading())
                }.collect {
                    emit(it)
                }
            }
        }

    val movie = _movie

    fun getMovieDetail(id: Int) {
        _id.value = id
    }
}
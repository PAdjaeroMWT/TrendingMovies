package com.example.trendingmovies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.trendingmovies.R
import com.example.trendingmovies.databinding.ActivityMovieDetailBinding
import com.example.trendingmovies.model.MovieDescription
import com.example.trendingmovies.model.ServerResult
import com.example.trendingmovies.utils.Constants
import com.example.trendingmovies.viewmodel.MovieDetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private val movieDetailViewModel by viewModels<MovieDetailViewModel>()
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent?.getIntExtra("movie_id", 0)?.let {
            movieDetailViewModel.getMovieDetail(it)
            initUi()
        } ?: displayErrorMessage("Server Error")

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun updateUi(movieDescription: MovieDescription) {
        title = movieDescription.title
        binding.detailMovieNameTextView.text = movieDescription.title
        binding.movieDescriptionTv.text = movieDescription.overview

        Glide.with(this).load(Constants.IMAGE_URL + movieDescription.poster_path).apply(
            RequestOptions().override(600 * 1000).centerInside().placeholder(R.drawable.placeholder)
        ).into(binding.detailMovieImgView)

        val genres = mutableListOf<String>()
        movieDescription.genres.map {
            genres.add(it.name)
        }

        binding.detailMovieGenreTextView.text = genres.joinToString(separator = ", ")
    }

    private fun displayErrorMessage(errorMessage: String) {
        val errorSnackBar = Snackbar.make(binding.detailContainer, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackBar.show()
    }

    private fun initUi() {
        movieDetailViewModel.movie.observe(this) { serverResult ->
            when (serverResult.status) {
                ServerResult.Status.LOADING -> {
                    binding.movieListProgressBar.visibility = View.VISIBLE
                }

                ServerResult.Status.SUCCESS -> {
                    serverResult.data?.let {
                        updateUi(it)
                    }
                    binding.movieListProgressBar.visibility = View.GONE
                }

                ServerResult.Status.ERROR -> {
                    serverResult.message?.let {
                        displayErrorMessage(it)
                    }
                }
            }
        }
    }
}
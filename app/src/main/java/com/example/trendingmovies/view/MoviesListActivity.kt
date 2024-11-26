package com.example.trendingmovies.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trendingmovies.R
import com.example.trendingmovies.databinding.ActivityMainBinding
import com.example.trendingmovies.model.Movie
import com.example.trendingmovies.model.ServerResult
import com.example.trendingmovies.view.adapters.MovieListAdapter
import com.example.trendingmovies.viewmodel.MoviesListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesListActivity : AppCompatActivity() {
    private val TAG: String = MoviesListActivity::class.java.simpleName

    private val list = ArrayList<Movie>()
    private val listViewModel by viewModels<MoviesListViewModel>()
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.main_activity_title)
        val layoutManager = LinearLayoutManager(this)
        binding.movieListRecyclerView.layoutManager = layoutManager
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val dividerItemDecoration = DividerItemDecoration(
            binding.movieListRecyclerView.context,
            layoutManager.orientation
        )

        binding.movieListRecyclerView.addItemDecoration(dividerItemDecoration)
        movieListAdapter = MovieListAdapter(this, list)
        binding.movieListRecyclerView.adapter = movieListAdapter

        initUi()
        Log.d(TAG, list.toString())
    }

    private fun initUi(){
        listViewModel.movieList.observe(this) { result ->

            when (result.status) {

                ServerResult.Status.LOADING -> {
                    binding.movieListProgressBar.visibility = View.VISIBLE
                }

                ServerResult.Status.SUCCESS -> {
                    result.data?.results?.let { updatedList ->
                        Log.d(TAG, updatedList.toString())
                        movieListAdapter.updateData(updatedList)
                    }
                    binding.movieListProgressBar.visibility = View.GONE
                }

                ServerResult.Status.ERROR -> {
                    result.message?.let {
                        displayErrorMessage(it)
                    }
                    binding.movieListProgressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun displayErrorMessage(errorMessage: String){
        val errorSnackBar = Snackbar.make(binding.listContainer, errorMessage, Snackbar.LENGTH_LONG)
        errorSnackBar.show()
    }


}
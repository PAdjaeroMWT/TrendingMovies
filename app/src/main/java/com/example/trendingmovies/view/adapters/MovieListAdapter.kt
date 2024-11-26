package com.example.trendingmovies.view.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.trendingmovies.R
import com.example.trendingmovies.databinding.MovieItemBinding
import com.example.trendingmovies.model.Movie
import com.example.trendingmovies.utils.Constants
import com.example.trendingmovies.utils.GenreMap
import com.example.trendingmovies.view.MovieDetailActivity

/**
 * adapter for list of movies recyclerview
 */
class MovieListAdapter(private val context: Context, private val list: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieListAdapter.MovieVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(layoutInflater, parent, false)
        return MovieVH(context, binding)
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(newList: List<Movie>) {
        list.clear()
        list.addAll(newList)
        Log.d("MovieListAdapter", list.toString())
        notifyDataSetChanged()
        Log.d("MovieListAdapter", list.toString())
    }

    class MovieVH(private val context: Context, private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            itemView.setOnClickListener {
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("movie_id", movie.id)
                context.startActivity(intent)
            }
            binding.movieItemNameTextView.text = movie.title

            Glide.with(context).load(Constants.IMAGE_URL + movie.poster_path).apply(
                RequestOptions().override(350, 700).fitCenter().placeholder(
                    R.drawable.placeholder
                )
            ).into(binding.moviePosterImgView)

            binding.movieItemGenreTextView.text = GenreMap.getGenre(movie.genre_ids)
        }
    }
}
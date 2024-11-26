package com.example.trendingmovies.model

data class MovieDescription(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String,
    val genres: List<Genres>
)

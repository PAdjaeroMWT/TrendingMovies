package com.example.trendingmovies.repository.cache

import androidx.room.*
import com.example.trendingmovies.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie order by popularity DESC")
    fun selectAll(): List<Movie>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Delete
    fun deleteMovie(movie: Movie)

    @Delete
    fun deleteAll(movies: List<Movie>)

    @Update
    fun updateAll(movies: List<Movie>)
}
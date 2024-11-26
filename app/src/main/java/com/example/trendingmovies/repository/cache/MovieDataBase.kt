package com.example.trendingmovies.repository.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trendingmovies.model.Movie
import com.example.trendingmovies.repository.GenreConverter

@Database(entities = [Movie::class], version = 1)
@TypeConverters(GenreConverter::class)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun MovieDao(): MovieDao
}
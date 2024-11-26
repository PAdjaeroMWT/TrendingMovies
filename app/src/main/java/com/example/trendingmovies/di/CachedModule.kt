package com.example.trendingmovies.di

import android.content.Context
import androidx.room.Room
import com.example.trendingmovies.repository.cache.MovieDao
import com.example.trendingmovies.repository.cache.MovieDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CachedModule {

    @Provides
    @Singleton
    fun injectRoomDataBase(@ApplicationContext applicationContext: Context): MovieDataBase {
        return Room.databaseBuilder(
            applicationContext,
            MovieDataBase::class.java,
            "movie.db"
        ).build()
    }

    @Provides
    fun injectMovieDao(movieDataBase: MovieDataBase): MovieDao {
        return movieDataBase.MovieDao()
    }
}
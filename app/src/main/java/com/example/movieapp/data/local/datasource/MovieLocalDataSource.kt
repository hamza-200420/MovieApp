package com.example.movieapp.data.local.datasource

import com.example.movieapp.data.local.dao.MovieDao
import com.example.movieapp.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {
    suspend fun insertMovie(movie: MovieEntity) {
        movieDao.insertMovie(movie)
    }

    suspend fun deleteMovieById(movieId: Int) {
        movieDao.deleteMovieById(movieId)
    }

    suspend fun isMovieSaved(movieId: Int): Boolean {
        return movieDao.isMovieSaved(movieId)
    }

    fun getAllMovies(): Flow<List<MovieEntity>> {
        return movieDao.getAllMovies()
    }
}
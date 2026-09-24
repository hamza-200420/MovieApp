package com.example.movieapp.domain.repository

import androidx.paging.PagingData
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDbModel
import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.model.MoviePage
import com.example.movieapp.domain.model.Region
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getNowPlaying(page: Int = 1): MoviePage
    suspend fun getTrendingWeek(page: Int = 1): MoviePage
    suspend fun getUpcoming(page: Int = 1): MoviePage
    suspend fun getGenres(): List<Genre>
    fun getTrendingWeekPaged(): Flow<PagingData<Movie>>
    fun getUpcomingPaged(): Flow<PagingData<Movie>>
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    fun searchMoviesPaged(query: String): Flow<PagingData<Movie>>
    suspend fun getRegions(): List<Region>
    suspend fun getMovieGenres(): List<Genre>
    suspend fun getTvGenres(): List<Genre>
    fun getFilteredMoviesPaged(
        region: String? = null,
        genreIds: String? = null,
        timePeriod: Int? = null,
        sortBy: String? = null
    ): Flow<PagingData<Movie>>

    fun getFilteredTvPaged(
        region: String? = null,
        genreIds: String? = null,
        timePeriod: Int? = null,
        sortBy: String? = null
    ): Flow<PagingData<Movie>>

    suspend fun insertMovie(movie: MovieDbModel)
    suspend fun deleteMovie(movieId: Int)
    suspend fun isMovieSaved(movieId: Int): Boolean
    fun getAllMovies(): Flow<List<MovieDbModel>>
}
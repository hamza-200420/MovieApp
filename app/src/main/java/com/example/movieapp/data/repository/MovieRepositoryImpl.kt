package com.example.movieapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movieapp.data.local.datasource.MovieLocalDataSource
import com.example.movieapp.data.local.mapper.toDomain
import com.example.movieapp.data.local.mapper.toEntity
import com.example.movieapp.data.remote.datasource.MovieRemoteDataSource
import com.example.movieapp.data.remote.mapper.toDomain
import com.example.movieapp.data.remote.paging.GenericMoviePagingSource
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDbModel
import com.example.movieapp.domain.model.MovieDetails
import com.example.movieapp.domain.model.MoviePage
import com.example.movieapp.domain.model.Region
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getNowPlaying(page: Int): MoviePage {
        return remoteDataSource.getNowPlaying(page).toDomain()
    }

    override suspend fun getTrendingWeek(page: Int): MoviePage {
        return remoteDataSource.getTrendingWeek(page).toDomain()
    }

    override suspend fun getUpcoming(page: Int): MoviePage {
        return remoteDataSource.getUpcoming(page).toDomain()
    }

    override suspend fun getGenres(): List<Genre> {
        return remoteDataSource.getGenres().genres.map { it.toDomain() }
    }

    override fun getTrendingWeekPaged(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                GenericMoviePagingSource { page ->
                    remoteDataSource.getTrendingWeek(page).toDomain()
                }
            }
        ).flow
    }

    override fun getUpcomingPaged(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                GenericMoviePagingSource { page ->
                    remoteDataSource.getUpcoming(page).toDomain()
                }
            }
        ).flow
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return remoteDataSource.getMovieDetails(movieId).toDomain()
    }

    override fun searchMoviesPaged(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                GenericMoviePagingSource { page ->
                    remoteDataSource.searchMovies(query, page).toDomain()
                }
            }
        ).flow
    }

    override suspend fun getRegions(): List<Region> {
        return remoteDataSource.getCountries().map { it.toDomain() }
    }

    override suspend fun getMovieGenres(): List<Genre> {
        return remoteDataSource.getMovieGenres().genres.map { it.toDomain() }
    }

    override suspend fun getTvGenres(): List<Genre> {
        return remoteDataSource.getTvGenres().genres.map { it.toDomain() }
    }

    override fun getFilteredMoviesPaged(
        region: String?,
        genreIds: String?,
        timePeriod: Int?,
        sortBy: String?
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                GenericMoviePagingSource { page ->
                    remoteDataSource.getFilteredMovies(
                        region = region,
                        genreIds = genreIds,
                        timePeriod = timePeriod,
                        sortBy = sortBy,
                        page = page
                    ).toDomain()
                }
            }
        ).flow
    }

    override fun getFilteredTvPaged(
        region: String?,
        genreIds: String?,
        timePeriod: Int?,
        sortBy: String?
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = {
                GenericMoviePagingSource { page ->
                    remoteDataSource.getFilteredTv(
                        region = region,
                        genreIds = genreIds,
                        timePeriod = timePeriod,
                        sortBy = sortBy,
                        page = page
                    ).toDomain()
                }
            }
        ).flow
    }

    override suspend fun insertMovie(movie: MovieDbModel) {
        localDataSource.insertMovie(movie.toEntity())
    }

    override suspend fun deleteMovie(movieId: Int) {
        localDataSource.deleteMovieById(movieId)
    }

    override suspend fun isMovieSaved(movieId: Int): Boolean {
        return localDataSource.isMovieSaved(movieId)
    }

    override fun getAllMovies(): Flow<List<MovieDbModel>> {
        return localDataSource.getAllMovies().map { entities ->
            entities.map { it.toDomain() }
        }
    }
}
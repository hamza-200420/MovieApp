package com.example.movieapp.data.datasource

import com.example.movieapp.data.api.MovieApi
import com.example.movieapp.data.dto.CountryDto
import com.example.movieapp.data.dto.GenreListDto
import com.example.movieapp.data.dto.MovieDetailsDto
import com.example.movieapp.data.dto.MoviePageDto
import com.example.movieapp.data.dto.TvPageDto
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val api: MovieApi
) {
    suspend fun getNowPlaying(page: Int): MoviePageDto {
        return api.nowPlaying(page = page)
    }

    suspend fun getTrendingWeek(page: Int): MoviePageDto {
        return api.trendingWeek(page = page)
    }

    suspend fun getUpcoming(page: Int): MoviePageDto {
        return api.upcoming(page = page)
    }

    suspend fun getGenres(): GenreListDto {
        return api.movieGenres()
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetailsDto {
        return api.getMovieDetails(movieId = movieId)
    }

    suspend fun searchMovies(query: String, page: Int): MoviePageDto {
        return api.searchMovies(query = query, page = page)
    }

    suspend fun getMovieGenres(): GenreListDto {
        return api.movieGenres()
    }

    suspend fun getTvGenres(): GenreListDto {
        return api.tvGenres()
    }

    suspend fun getCountries(): List<CountryDto> {
        return api.getCountries()
    }

    suspend fun getFilteredMovies(
        region: String? = null,
        genreIds: String? = null,
        timePeriod: Int? = null,
        sortBy: String? = null,
        page: Int = 1
    ): MoviePageDto {
        return api.discoverMovies(
            page = page,
            sortBy = sortBy,
            withGenres = genreIds,
            withOriginCountry = region,
            primaryReleaseYear = timePeriod
        )
    }

    suspend fun getFilteredTv(
        region: String? = null,
        genreIds: String? = null,
        timePeriod: Int? = null,
        sortBy: String? = null,
        page: Int = 1
    ): TvPageDto {
        return api.discoverTv(
            page = page,
            sortBy = sortBy,
            withGenres = genreIds,
            withOriginCountry = region,
            firstAirDateYear = timePeriod
        )
    }
}
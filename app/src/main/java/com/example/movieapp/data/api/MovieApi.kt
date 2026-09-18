package com.example.movieapp.data.api

import com.example.movieapp.BuildConfig
import com.example.movieapp.data.dto.CountryDto
import com.example.movieapp.data.dto.GenreListDto
import com.example.movieapp.data.dto.MovieDetailsDto
import com.example.movieapp.data.dto.MoviePageDto
import com.example.movieapp.data.dto.TvPageDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/now_playing")
    suspend fun nowPlaying(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int,
    ): MoviePageDto

    @GET("trending/movie/week")
    suspend fun trendingWeek(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int
    ): MoviePageDto

    @GET("movie/upcoming")
    suspend fun upcoming(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int,
    ): MoviePageDto

    @GET("genre/movie/list")
    suspend fun movieGenres(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US"
    ): GenreListDto

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("append_to_response") appendToResponse: String = "credits,videos,reviews,similar,release_dates"
    ): MovieDetailsDto

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("include_adult") includeAdult: Boolean = false
    ): MoviePageDto


    @GET("genre/tv/list")
    suspend fun tvGenres(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US"
    ): GenreListDto

    @GET("configuration/countries")
    suspend fun getCountries(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US"
    ): List<CountryDto>


    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String? = "popularity.desc",
        @Query("with_genres") withGenres: String? = null,
        @Query("with_origin_country") withOriginCountry: String? = null,
        @Query("primary_release_year") primaryReleaseYear: Int? = null,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US"
    ): MoviePageDto

    @GET("discover/tv")
    suspend fun discoverTv(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String? = "popularity.desc",
        @Query("with_genres") withGenres: String? = null,
        @Query("with_origin_country") withOriginCountry: String? = null,
        @Query("first_air_date_year") firstAirDateYear: Int? = null,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US"
    ): TvPageDto
}
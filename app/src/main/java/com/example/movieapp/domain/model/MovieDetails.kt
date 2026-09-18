package com.example.movieapp.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val tagline: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val runtime: Int?,
    val releaseDate: String?,
    val certification: String?,
    val genres: List<Genre>,
    val cast: List<CastMember>,
    val trailers: List<Video>,
    val similarMovies: List<Movie>,
    val reviews: List<Review>,
    val releaseCountry: String?,
)
package com.example.movieapp.domain.model

data class MovieDbModel(
    val id: Long = 0,
    val movieId: Int,
    val title: String,
    val posterPath: String?,
    val voteAverage: Double
)
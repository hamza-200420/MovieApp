package com.example.movieapp.data.remote.dto

data class MovieFirestoreDto(
    val id: Long = 0,
    val movieId: Int = 0,
    val title: String = "",
    val posterPath: String? = null,
    val voteAverage: Double = 0.0,
    val addedAt: Long = 0,
    val mediaType: String = "movie"
)
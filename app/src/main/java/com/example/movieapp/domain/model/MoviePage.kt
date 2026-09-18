package com.example.movieapp.domain.model


data class MoviePage(
    val movies: List<Movie>,
    val page: Int,
    val totalPages: Int
)
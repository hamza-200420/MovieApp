package com.example.movieapp.presentation

import com.example.movieapp.domain.model.MovieDetails

data class DetailsScreenUiState(
    val isLoading: Boolean = true,
    val movieDetails: MovieDetails? = null,
//    val errorMessage: String? = null,
    val selectedTabIndex: Int = 0
)
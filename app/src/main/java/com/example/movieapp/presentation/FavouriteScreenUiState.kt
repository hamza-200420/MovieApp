package com.example.movieapp.presentation

import com.example.movieapp.domain.model.MovieDbModel

data class FavouriteScreenUiState(
    val movies: List<MovieDbModel> = emptyList(),
    val isLoading: Boolean=false
)
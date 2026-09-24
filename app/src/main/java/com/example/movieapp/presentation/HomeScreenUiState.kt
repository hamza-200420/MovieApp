package com.example.movieapp.presentation

import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.MoviePage

data class HomeScreenUiState(
    val bannerObj: MoviePage? = null,
    val upcomingObj: MoviePage? = null,
    val topTenObj: MoviePage? = null,
    val genreList: List<Genre> = emptyList(),
    val genreNames: List<String> = emptyList(),
    val favoriteIds: Set<Int> = emptySet()
)
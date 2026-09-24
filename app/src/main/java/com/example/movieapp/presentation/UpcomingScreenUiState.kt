package com.example.movieapp.presentation

data class UpcomingScreenUiState(
    val searchQuery: String = "",
    val favoriteIds: Set<Int> = emptySet()
)
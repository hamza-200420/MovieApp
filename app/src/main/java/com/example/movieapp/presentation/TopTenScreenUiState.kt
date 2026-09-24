package com.example.movieapp.presentation

data class TopTenScreenUiState(
    val searchQuery: String = "",
    val favoriteIds: Set<Int> = emptySet()
)
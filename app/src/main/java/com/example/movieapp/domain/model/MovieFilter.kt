package com.example.movieapp.domain.model

data class MovieFilter(
    val category: Category = Category.MOVIE,
    val regions: Set<Region> = emptySet(),
    val genres: Set<Genre> = emptySet(),
    val year: Int? = null,
    val sortBy: SortOption = SortOption.POPULARITY
)
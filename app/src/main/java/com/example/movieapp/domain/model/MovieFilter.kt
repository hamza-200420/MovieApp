package com.example.movieapp.domain.model

data class MovieFilter(
    val category: Category = Category.MOVIE,
    val region: Region? = null,
    val genres: Set<Genre> = emptySet(),
    val year: Int? = null,
    val sortBy: SortOption = SortOption.POPULARITY
)
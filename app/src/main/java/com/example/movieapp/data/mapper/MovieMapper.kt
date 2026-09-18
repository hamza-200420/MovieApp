package com.example.movieapp.data.mapper

import com.example.movieapp.data.dto.MovieDto
import com.example.movieapp.data.dto.MoviePageDto
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MoviePage

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        genreIds = genreIds ?: emptyList()
    )
}

fun MoviePageDto.toDomain(): MoviePage {
    return MoviePage(
        movies = results.map { it.toDomain() },
        page = page,
        totalPages = totalPages
    )
}
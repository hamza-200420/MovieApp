package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.dto.TvDto
import com.example.movieapp.data.remote.dto.TvPageDto
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MoviePage

fun TvDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = name,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        genreIds = genreIds ?: emptyList()
    )
}

fun TvPageDto.toDomain(): MoviePage {
    return MoviePage(
        page = page,
        movies = results.map { it.toDomain() },
        totalPages = totalPages
    )
}
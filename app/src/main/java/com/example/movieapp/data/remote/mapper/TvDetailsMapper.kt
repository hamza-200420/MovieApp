package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.dto.TvDetailsDto
import com.example.movieapp.domain.model.MovieDetails
import java.util.Locale

fun TvDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = name,
        overview = overview,
        tagline = tagline,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        runtime = episodeRunTime?.firstOrNull(),
        releaseDate = firstAirDate,
        certification = contentRatings?.results
            ?.firstOrNull { it.countryCode == "US" }
            ?.rating
            ?.takeIf { it.isNotBlank() },
        genres = genres.map { it.toDomain() },
        cast = credits.cast.map { it.toDomain() },
        trailers = videos.results
            .filter { it.site == "YouTube" && it.type == "Trailer" }
            .map { it.toDomain() },
        similarMovies = similar.results.map { it.toDomain() },
        reviews = reviews.results.map { it.toDomain() },
        releaseCountry = originCountry?.firstOrNull()?.let { Locale("", it).displayCountry },
        mediaType = "tv"
    )
}
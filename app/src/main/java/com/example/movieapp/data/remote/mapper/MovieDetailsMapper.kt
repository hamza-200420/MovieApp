package com.example.movieapp.data.remote.mapper

import com.example.movieapp.data.remote.dto.CastMemberDto
import com.example.movieapp.data.remote.dto.MovieDetailsDto
import com.example.movieapp.data.remote.dto.ReleaseDatesResultDto
import com.example.movieapp.data.remote.dto.ReviewDto
import com.example.movieapp.data.remote.dto.VideoDto
import com.example.movieapp.domain.model.*
import java.util.Locale

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        tagline = tagline,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        runtime = runtime,
        releaseDate = releaseDate,
        certification = extractCertification(releaseDates),
        genres = genres.map { it.toDomain() },
        cast = credits.cast.map { it.toDomain() },
        trailers = videos.results
            .filter { it.site == "YouTube" && it.type == "Trailer" }
            .map { it.toDomain() },
        similarMovies = similar.results.map { it.toDomain() },
        reviews = reviews.results.map { it.toDomain() },
        releaseCountry = extractCountry(releaseDates),
    )
}

private fun extractCertification(dto: ReleaseDatesResultDto): String? {
    return dto.results
        .firstOrNull { it.countryCode == "US" }
        ?.releaseDates
        ?.firstOrNull { it.certification.isNotBlank() }
        ?.certification
}

private fun extractCountry(dto: ReleaseDatesResultDto): String? {
    val code = dto.results
        .firstOrNull { it.countryCode == "US" }
        ?.countryCode
    return code?.let { Locale("", it).displayCountry }
}

fun CastMemberDto.toDomain(): CastMember {
    return CastMember(
        id = id,
        name = name,
        character = character,
        profilePath = profilePath
    )
}

fun VideoDto.toDomain(): Video {
    return Video(
        key = key,
        name = name,
        site = site,
        type = type,
        official = official
    )
}

fun ReviewDto.toDomain(): Review {
    return Review(
        id = id,
        authorName = authorDetails.name,
        authorAvatarPath = authorDetails.avatarPath,
        content = content,
        rating = authorDetails.rating,
        createdAt = createdAt
    )
}


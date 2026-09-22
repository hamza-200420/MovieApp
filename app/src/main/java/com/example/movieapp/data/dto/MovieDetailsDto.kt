package com.example.movieapp.data.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("genres") val genres: List<com.example.movieapp.data.dto.GenreDto>,
    @SerializedName("credits") val credits: com.example.movieapp.data.dto.CreditsDto,
    @SerializedName("videos") val videos: com.example.movieapp.data.dto.VideoListDto,
    @SerializedName("reviews") val reviews: com.example.movieapp.data.dto.ReviewPageDto,
    @SerializedName("similar") val similar: com.example.movieapp.data.dto.MoviePageDto,
    @SerializedName("release_dates") val releaseDates: com.example.movieapp.data.dto.ReleaseDatesResultDto
)
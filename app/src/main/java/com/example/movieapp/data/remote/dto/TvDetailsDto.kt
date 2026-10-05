package com.example.movieapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class TvDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("episode_run_time") val episodeRunTime: List<Int>?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("genres") val genres: List<GenreDto>,
    @SerializedName("credits") val credits: CreditsDto,
    @SerializedName("videos") val videos: VideoListDto,
    @SerializedName("reviews") val reviews: ReviewPageDto,
    @SerializedName("similar") val similar: TvPageDto,
    @SerializedName("content_ratings") val contentRatings: ContentRatingsDto?
)

data class ContentRatingsDto(
    @SerializedName("results") val results: List<ContentRatingDto>
)

data class ContentRatingDto(
    @SerializedName("iso_3166_1") val countryCode: String,
    @SerializedName("rating") val rating: String
)
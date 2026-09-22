package com.example.movieapp.presentation

import androidx.paging.PagingData
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieFilter
import com.example.movieapp.domain.model.Region
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ExploreScreenUiState(
    val movies: Flow<PagingData<Movie>> = emptyFlow(),
    val availableRegions: List<Region> = emptyList(),
    val availableGenres: List<Genre> = emptyList(),
    val draftFilter: MovieFilter = MovieFilter(),
    val appliedFilter: MovieFilter = MovieFilter(),
    val activeFilterLabels: List<String> = emptyList(),
    val isFilterSheetVisible: Boolean = false
)
package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.domain.model.Category
import com.example.movieapp.domain.model.Genre
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieFilter
import com.example.movieapp.domain.model.Region
import com.example.movieapp.domain.model.SortOption
import com.example.movieapp.domain.usecase.GetFilteredMoviesPagedUseCase
import com.example.movieapp.domain.usecase.GetFilteredTvPagedUseCase
import com.example.movieapp.domain.usecase.GetMovieGenresUseCase
import com.example.movieapp.domain.usecase.GetRegionsUseCase
import com.example.movieapp.domain.usecase.GetTvGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreScreenViewModel @Inject constructor(
    private val getFilteredMoviesPagedUseCase: GetFilteredMoviesPagedUseCase,
    private val getFilteredTvPagedUseCase: GetFilteredTvPagedUseCase,
    private val getRegionsUseCase: GetRegionsUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getTvGenresUseCase: GetTvGenresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreScreenUiState())
    val uiState: StateFlow<ExploreScreenUiState> = _uiState.asStateFlow()

    private val moviesFlow: Flow<PagingData<Movie>> = _uiState
        .map { it.appliedFilter }
        .distinctUntilChanged()
        .flatMapLatest { filter ->
            when (filter.category) {
                Category.MOVIE -> getFilteredMoviesPagedUseCase(
                    region = filter.regionParam(),
                    genreIds = filter.genreParam(),
                    timePeriod = filter.year,
                    sortBy = filter.sortBy.apiValue
                )

                Category.TV_SHOWS -> getFilteredTvPagedUseCase(
                    region = filter.regionParam(),
                    genreIds = filter.genreParam(),
                    timePeriod = filter.year,
                    sortBy = filter.sortBy.apiValue
                )
            }
        }
        .cachedIn(viewModelScope)

    init {
        _uiState.update { it.copy(movies = moviesFlow) }
        loadRegions()
        loadGenresFor(Category.MOVIE)
    }

    fun onFilterIconClicked() {
        _uiState.update {
            it.copy(
                draftFilter = it.appliedFilter,
                isFilterSheetVisible = true
            )
        }
    }

    fun onDismissFilterSheet() {
        _uiState.update { it.copy(isFilterSheetVisible = false) }
    }

    fun onCategorySelected(category: Category) {
        _uiState.update {
            if (category == it.draftFilter.category) return@update it
            it.copy(
                draftFilter = it.draftFilter.copy(category = category, genres = emptySet())
            )
        }
        loadGenresFor(category)
    }

    fun onClearRegions() {
        _uiState.update {
            it.copy(draftFilter = it.draftFilter.copy(regions = emptySet()))
        }
    }

    fun onRegionToggled(region: Region) {
        _uiState.update {
            val currentRegions = it.draftFilter.regions
            val newRegions =
                if (region in currentRegions) currentRegions - region else currentRegions + region
            it.copy(draftFilter = it.draftFilter.copy(regions = newRegions))
        }
    }

    fun onClearGenres() {
        _uiState.update {
            it.copy(draftFilter = it.draftFilter.copy(genres = emptySet()))
        }
    }

    fun onGenreToggled(genre: Genre) {
        _uiState.update {
            val currentGenres = it.draftFilter.genres
            val newGenres =
                if (genre in currentGenres) currentGenres - genre else currentGenres + genre
            it.copy(draftFilter = it.draftFilter.copy(genres = newGenres))
        }
    }

    fun onYearToggled(year: Int?) {
        _uiState.update {
            val newYear = if (it.draftFilter.year == year) null else year
            it.copy(draftFilter = it.draftFilter.copy(year = newYear))
        }
    }

    fun onSortSelected(sort: SortOption) {
        _uiState.update {
            it.copy(draftFilter = it.draftFilter.copy(sortBy = sort))
        }
    }

    fun onApplyFilters() {
        _uiState.update {
            it.copy(
                appliedFilter = it.draftFilter,
                isFilterSheetVisible = false,
                activeFilterLabels = it.draftFilter.toActiveLabels()
            )
        }
    }

    fun onResetFilters() {
        _uiState.update {
            it.copy(
                draftFilter = MovieFilter(category = it.draftFilter.category)
            )
        }
    }

    private fun loadRegions() {
        viewModelScope.launch {
            val regions = getRegionsUseCase()
            _uiState.update { it.copy(availableRegions = regions) }
        }
    }

    private fun loadGenresFor(category: Category) {
        viewModelScope.launch {
            val genres = when (category) {
                Category.MOVIE -> getMovieGenresUseCase()
                Category.TV_SHOWS -> getTvGenresUseCase()
            }
            _uiState.update { it.copy(availableGenres = genres) }
        }
    }

    private fun MovieFilter.regionParam(): String? {
        return regions.takeIf { it.isNotEmpty() }?.joinToString("|") { it.isoCode }
    }

    private fun MovieFilter.genreParam(): String? {
        return genres.takeIf { it.isNotEmpty() }?.joinToString("|") { it.id.toString() }
    }

    private fun MovieFilter.toActiveLabels(): List<String> {
        val labels = mutableListOf<String>()
        labels += genres.map { it.name }
        labels += regions.map { it.englishName }
        year?.let { labels += it.toString() }
        return labels
    }
}
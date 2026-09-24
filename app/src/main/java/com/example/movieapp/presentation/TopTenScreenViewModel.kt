package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDbModel
import com.example.movieapp.domain.usecase.DeleteMovieUseCase
import com.example.movieapp.domain.usecase.GetAllMoviesUseCase
import com.example.movieapp.domain.usecase.GetSearchMoviesPagedUseCase
import com.example.movieapp.domain.usecase.GetTrendingWeekPagedUseCase
import com.example.movieapp.domain.usecase.InsertMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopTenScreenViewModel @Inject constructor(
    getTrendingWeekPagedUseCase: GetTrendingWeekPagedUseCase,
    getSearchMoviesPagedUseCase: GetSearchMoviesPagedUseCase,
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase
) : ViewModel() {
    init {
        getFavorites()
    }

    private val _uiState = MutableStateFlow(TopTenScreenUiState())
    val uiState: StateFlow<TopTenScreenUiState> = _uiState.asStateFlow()
    val moviesPagingFlow: Flow<PagingData<Movie>> =
        getTrendingWeekPagedUseCase().cachedIn(viewModelScope)

    private fun getFavorites() {
        viewModelScope.launch {
            getAllMoviesUseCase().collect { movies ->
                val ids = movies.map { it.movieId }.toSet()
                _uiState.update { it.copy(favoriteIds = ids) }
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            if (movie.id in _uiState.value.favoriteIds) {
                deleteMovieUseCase(movie.id)
            } else {
                insertMovieUseCase(
                    MovieDbModel(
                        id = 0,
                        movieId = movie.id,
                        title = movie.title,
                        posterPath = movie.posterPath,
                        voteAverage = movie.voteAverage
                    )
                )
            }
        }
    }

    val searchResultsFlow: Flow<PagingData<Movie>> = _uiState
        .map { it.searchQuery.trim() }
        .distinctUntilChanged()
        .debounce(400)
        .filter { it.isNotBlank() }
        .flatMapLatest { query -> getSearchMoviesPagedUseCase(query) }
        .cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}
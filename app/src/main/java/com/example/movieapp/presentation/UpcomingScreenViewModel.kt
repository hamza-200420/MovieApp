package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDbModel
import com.example.movieapp.domain.usecase.CheckUserLoggedInUseCase
import com.example.movieapp.domain.usecase.DeleteMovieUseCase
import com.example.movieapp.domain.usecase.GetAllMoviesUseCase
import com.example.movieapp.domain.usecase.GetSearchMoviesPagedUseCase
import com.example.movieapp.domain.usecase.GetUpcomingPagedUseCase
import com.example.movieapp.domain.usecase.InsertMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class UpcomingScreenViewModel @Inject constructor(
    getUpcomingPagedUseCase: GetUpcomingPagedUseCase,
    getSearchMoviesPagedUseCase: GetSearchMoviesPagedUseCase,
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val userLoggedInUseCase: CheckUserLoggedInUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UpcomingScreenUiState())
    val uiState: StateFlow<UpcomingScreenUiState> = _uiState.asStateFlow()

    val moviesPagingFlow: Flow<PagingData<Movie>> =
        getUpcomingPagedUseCase().cachedIn(viewModelScope)

    val searchResultsFlow: Flow<PagingData<Movie>> = _uiState
        .map { it.searchQuery.trim() }
        .distinctUntilChanged()
        .debounce(400)
        .filter { it.isNotBlank() }
        .flatMapLatest { query -> getSearchMoviesPagedUseCase(query) }
        .cachedIn(viewModelScope)

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            getAllMoviesUseCase().collect { movies ->
                // only movie favourites, so a saved TV show with the same id doesn't light up a movie
                val ids = movies
                    .filter { it.mediaType == "movie" }
                    .map { it.movieId }
                    .toSet()
                _uiState.update { it.copy(favoriteIds = ids) }
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        if (!userLoggedInUseCase()) {
            _uiState.update { it.copy(showLoginPrompt = true) }
            return
        }
        val isFavorite = movie.id in _uiState.value.favoriteIds
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (isFavorite) {
                    deleteMovieUseCase(movie.id, "movie")
                } else {
                    insertMovieUseCase(
                        MovieDbModel(
                            id = 0,
                            movieId = movie.id,
                            title = movie.title,
                            posterPath = movie.posterPath,
                            voteAverage = movie.voteAverage,
                            mediaType = "movie"
                        )
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
            }
        }
    }

    fun onLoginPromptShown() {
        _uiState.update { it.copy(showLoginPrompt = false) }
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }
}
package com.example.movieapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.MovieDbModel
import com.example.movieapp.domain.usecase.CheckUserLoggedInUseCase
import com.example.movieapp.domain.usecase.DeleteMovieUseCase
import com.example.movieapp.domain.usecase.GetAllMoviesUseCase
import com.example.movieapp.domain.usecase.GetMovieDetailsUseCase
import com.example.movieapp.domain.usecase.InsertMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val userLoggedInUseCase: CheckUserLoggedInUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsScreenUiState())
    val uiState: StateFlow<DetailsScreenUiState> = _uiState.asStateFlow()

    private val movieId: Int = savedStateHandle.get<Int>("movieId")
        ?: error("movieId missing from nav args")

    init {
        loadMovies()
        observeSavedStatus()
    }

    private fun loadMovies() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val res = getMovieDetailsUseCase(movieId)
            delay(400.milliseconds)
            _uiState.update { it.copy(isLoading = false, movieDetails = res) }
        }
    }
    private fun observeSavedStatus() {
        viewModelScope.launch {
            getAllMoviesUseCase().collect { movies ->
                _uiState.update { state ->
                    state.copy(isSaved = movies.any { it.movieId == movieId })
                }
            }
        }
    }

    fun toggleIcon() {
        if (!userLoggedInUseCase()) {
            _uiState.update { it.copy(showLoginPrompt = true) }
            return
        }
        val details = _uiState.value.movieDetails ?: return
        val currentlySaved = _uiState.value.isSaved

        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (currentlySaved) {
                    deleteMovieUseCase(movieId)
                } else {
                    insertMovieUseCase(
                        MovieDbModel(
                            id = 0,
                            movieId = movieId,
                            title = details.title,
                            posterPath = details.posterPath,
                            voteAverage = details.voteAverage
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

    fun updateTabIndex(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }
}
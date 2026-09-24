package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.usecase.DeleteMovieUseCase
import com.example.movieapp.domain.usecase.GetAllMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(FavouriteScreenUiState())
    val uiState: StateFlow<FavouriteScreenUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getAllMoviesUseCase().collect { movies ->
                _uiState.update { it.copy(movies = movies, isLoading = false) }
            }

        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteMovieUseCase(id)
        }
    }
}
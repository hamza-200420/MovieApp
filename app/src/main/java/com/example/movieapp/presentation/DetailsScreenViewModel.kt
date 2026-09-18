package com.example.movieapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.usecase.GetMovieDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(DetailsScreenUiState())
    val uiState: StateFlow<DetailsScreenUiState> = _uiState.asStateFlow()

    private val movieId: Int = savedStateHandle.get<Int>("movieId")
        ?: error("movieId missing from nav args")

    init {
        loadMovies()
    }

    private fun loadMovies() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val res = getMovieDetailsUseCase(movieId)
            _uiState.update { it.copy(isLoading = false, movieDetails = res) }
        }
    }

    fun updateTabIndex(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }
}
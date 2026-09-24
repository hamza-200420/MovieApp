package com.example.movieapp.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.MovieDbModel
import com.example.movieapp.domain.usecase.DeleteMovieUseCase
import com.example.movieapp.domain.usecase.GetIsMovieSavedUseCase
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
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val getIsMovieSavedUseCase: GetIsMovieSavedUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private val _uiState = MutableStateFlow(DetailsScreenUiState())
    val uiState: StateFlow<DetailsScreenUiState> = _uiState.asStateFlow()

    private val movieId: Int = savedStateHandle.get<Int>("movieId")
        ?: error("movieId missing from nav args")

    init {
        loadMovies()
        getSavedStatus()
    }

    private fun loadMovies() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            val res = getMovieDetailsUseCase(movieId)
            delay(400.milliseconds)
            _uiState.update { it.copy(isLoading = false, movieDetails = res) }
        }
    }

    private fun getSavedStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getIsMovieSavedUseCase(movieId)
            _uiState.update { it.copy(isSaved = res) }
        }
    }

    fun toggleIcon() {
        if (_uiState.value.isSaved) {
            viewModelScope.launch(Dispatchers.IO) {
                deleteMovieUseCase(movieId)
            }
        } else
            viewModelScope.launch(Dispatchers.IO) {
                val res = _uiState.value.movieDetails
                insertMovieUseCase(
                    MovieDbModel(
                        0,
                        res!!.id,
                        res.title,
                        res.posterPath,
                        res.voteAverage
                    )
                )
            }
        _uiState.update { it.copy(isSaved = !_uiState.value.isSaved) }
    }

    fun updateTabIndex(index: Int) {
        _uiState.update { it.copy(selectedTabIndex = index) }
    }
}
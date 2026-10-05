package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.usecase.CheckUserLoggedInUseCase
import com.example.movieapp.domain.usecase.DeleteMovieUseCase
import com.example.movieapp.domain.usecase.GetAllMoviesUseCase
import com.example.movieapp.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class FavouriteScreenViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavouriteScreenUiState())
    val uiState: StateFlow<FavouriteScreenUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getAllMoviesUseCase().collect { movies ->
                if (!checkUserLoggedInUseCase()) {
                    // Logged out: clear the list and keep the shimmer until the next login.
                    _uiState.update { it.copy(movies = emptyList(), isLoading = true) }
                } else {
                    _uiState.update { it.copy(movies = movies, isLoading = false) }
                }
            }
        }
    }

    fun removeFavorite(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                deleteMovieUseCase(id)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(logoutError = e.localizedMessage ?: "Could not remove favourite")
                }
            }
        }
    }

    fun isAlreadyLogin(): Boolean {
        return checkUserLoggedInUseCase()
    }

    fun logoutUser(onLoggedOut: () -> Unit) {
        if (uiState.value.isLoggingOut) return
        _uiState.update { it.copy(isLoggingOut = true, logoutError = "") }
        viewModelScope.launch {
            try {
                logoutUseCase()
                _uiState.update { it.copy(isLoggingOut = false) }
                onLoggedOut()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoggingOut = false,
                        logoutError = e.localizedMessage ?: "Logout failed, please try again"
                    )
                }
            }
        }
    }

    fun clearLogoutError() {
        _uiState.update { it.copy(logoutError = "") }
    }
}
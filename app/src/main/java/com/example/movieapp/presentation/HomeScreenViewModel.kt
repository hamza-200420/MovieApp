package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.usecase.GetMovieGenresUseCase
//import com.example.movieapp.domain.usecase.GetGenresUseCase
import com.example.movieapp.domain.usecase.GetNowPlayingUseCase
import com.example.movieapp.domain.usecase.GetTrendingWeekUseCase
import com.example.movieapp.domain.usecase.GetUpcomingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val getTrendingWeekUseCase: GetTrendingWeekUseCase,
    val getUpcomingUseCase: GetUpcomingUseCase,
    val getNowPlayingUseCase: GetNowPlayingUseCase,
    val movieGenresUseCase: GetMovieGenresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    init {
        getBannerMovie()
        getTopMovies()
        getUpcomingMovies()
    }

    private fun getBannerMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            val bannerResult = getNowPlayingUseCase()
            val genres = movieGenresUseCase()

            val genreNames = bannerResult.movies.firstOrNull()
                ?.genreIds
                ?.mapNotNull { id -> genres.find { it.id == id }?.name }
                ?: emptyList()

            _uiState.update {
                it.copy(
                    bannerObj = bannerResult,
                    genreList = genres,
                    genreNames = genreNames
                )
            }
        }
    }

    private fun getTopMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getTrendingWeekUseCase()
            _uiState.update { it.copy(topTenObj = result) }
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getUpcomingUseCase()
            _uiState.update { it.copy(upcomingObj = result) }
        }
    }

}
package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.model.MovieDbModel
import com.example.movieapp.domain.usecase.DeleteMovieUseCase
import com.example.movieapp.domain.usecase.GetAllMoviesUseCase
import com.example.movieapp.domain.usecase.GetMovieGenresUseCase
import com.example.movieapp.domain.usecase.GetNowPlayingUseCase
import com.example.movieapp.domain.usecase.GetTrendingWeekUseCase
import com.example.movieapp.domain.usecase.GetUpcomingUseCase
import com.example.movieapp.domain.usecase.InsertMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    val getTrendingWeekUseCase: GetTrendingWeekUseCase,
    val getUpcomingUseCase: GetUpcomingUseCase,
    val getNowPlayingUseCase: GetNowPlayingUseCase,
    val movieGenresUseCase: GetMovieGenresUseCase,
    private val getAllMoviesUseCase: GetAllMoviesUseCase,
    private val insertMovieUseCase: InsertMovieUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    init {
        getBannerMovie()
        getTopMovies()
        getUpcomingMovies()
        getFavorites()
    }

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
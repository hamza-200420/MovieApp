package com.example.movieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.usecase.GetSearchMoviesPagedUseCase
import com.example.movieapp.domain.usecase.GetTrendingWeekPagedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

@HiltViewModel
class TopTenScreenViewModel @Inject constructor(
    getTrendingWeekPagedUseCase: GetTrendingWeekPagedUseCase,
    getSearchMoviesPagedUseCase: GetSearchMoviesPagedUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TopTenScreenUiState())
    val uiState: StateFlow<TopTenScreenUiState> = _uiState.asStateFlow()

     val moviesPagingFlow: Flow<PagingData<Movie>> =
        getTrendingWeekPagedUseCase().cachedIn(viewModelScope)

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
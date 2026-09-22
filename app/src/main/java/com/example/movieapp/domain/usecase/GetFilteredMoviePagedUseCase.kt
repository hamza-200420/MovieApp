package com.example.movieapp.domain.usecase

import androidx.paging.PagingData
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilteredMoviesPagedUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(
        region: String? = null,
        genreIds: String? = null,
        timePeriod: Int? = null,
        sortBy: String? = null
    ): Flow<PagingData<Movie>> {
        return repository.getFilteredMoviesPaged(
            region = region,
            genreIds = genreIds,
            timePeriod = timePeriod,
            sortBy = sortBy
        )
    }
}
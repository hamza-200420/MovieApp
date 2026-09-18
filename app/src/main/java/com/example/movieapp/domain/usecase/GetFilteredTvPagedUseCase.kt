package com.example.movieapp.domain.usecase

import androidx.paging.PagingData
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilteredTvPagedUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(
        region: String? = null,
        genreId: Int? = null,
        timePeriod: Int? = null,
        sortBy: String? = null
    ): Flow<PagingData<Movie>> {
        return repository.getFilteredTvPaged(
            region = region,
            genreId = genreId,
            timePeriod = timePeriod,
            sortBy = sortBy
        )
    }
}
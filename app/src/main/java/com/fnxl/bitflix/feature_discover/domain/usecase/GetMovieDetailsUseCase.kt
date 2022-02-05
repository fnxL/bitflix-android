package com.fnxl.bitflix.feature_discover.domain.usecase

import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.domain.model.movie.Movie
import com.fnxl.bitflix.feature_discover.domain.repository.DiscoverRepository

class GetMovieDetailsUseCase(private val repository: DiscoverRepository) {
    suspend operator fun invoke(
        id: String,
        append: String
    ): Resource<Movie> {
        return repository.getMovieDetails(id = id, append = append)
    }
}
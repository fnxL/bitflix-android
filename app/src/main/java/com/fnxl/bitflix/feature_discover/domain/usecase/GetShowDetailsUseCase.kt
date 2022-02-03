package com.fnxl.bitflix.feature_discover.domain.usecase

import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.TVShow
import com.fnxl.bitflix.feature_discover.domain.repository.DiscoverRepository

class GetShowDetailsUseCase(private val repository: DiscoverRepository) {
    suspend operator fun invoke(
        id: String,
        append: String
    ): Resource<TVShow> {
        return repository.getShowDetails(id, append = append)
    }
}
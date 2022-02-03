package com.fnxl.bitflix.feature_discover.domain.usecase

import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.domain.model.tvshow.season.Season
import com.fnxl.bitflix.feature_discover.domain.repository.DiscoverRepository

class GetSeasonDetailsUseCase(private val repository: DiscoverRepository) {
    suspend operator fun invoke(
        id: String,
        seasonNumber: String,
    ): Resource<Season> {
        return repository.getSeasonDetails(id, seasonNumber)
    }
}
package com.fnxl.bitflix.feature_discover.domain.usecase

import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem
import com.fnxl.bitflix.feature_discover.domain.repository.DiscoverRepository

class GetTrendingUseCase(private val repository: DiscoverRepository) {
    suspend operator fun invoke(mediaType: MediaType): Resource<List<ResultItem>> {
        return repository.getTrendingList(mediaType = mediaType)
    }
}
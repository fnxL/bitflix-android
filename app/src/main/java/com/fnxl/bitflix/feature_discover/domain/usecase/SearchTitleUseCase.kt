package com.fnxl.bitflix.feature_discover.domain.usecase

import androidx.paging.PagingData
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem
import com.fnxl.bitflix.feature_discover.domain.model.movie.Movie
import com.fnxl.bitflix.feature_discover.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow

class SearchTitleUseCase(private val repository: DiscoverRepository) {
    operator fun invoke(
        query: String
    ): Flow<PagingData<ResultItem>> {
        return repository.searchTitles(query = query)
    }
}
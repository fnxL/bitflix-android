package com.fnxl.bitflix.feature_discover.domain.usecase

import com.fnxl.bitflix.core.domain.models.RowData
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem
import com.fnxl.bitflix.feature_discover.domain.repository.DiscoverRepository

class GetRowDataUseCase(private val repository: DiscoverRepository) {
    suspend operator fun invoke(
        rowData: RowData,
        mediaType: MediaType
    ): Resource<List<ResultItem>> {
        return repository.getRowData(rowData = rowData, mediaType = mediaType)
    }
}
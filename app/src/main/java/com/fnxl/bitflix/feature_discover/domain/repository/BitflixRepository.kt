package com.fnxl.bitflix.feature_discover.domain.repository

import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.data.remote.StreamLinksRequest
import com.fnxl.bitflix.feature_discover.data.remote.response.StreamLinksResponse

interface BitflixRepository {

    suspend fun getStreamLinks(request: StreamLinksRequest): Resource<StreamLinksResponse>
}
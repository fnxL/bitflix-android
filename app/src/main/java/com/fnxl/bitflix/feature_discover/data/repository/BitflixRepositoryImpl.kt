package com.fnxl.bitflix.feature_discover.data.repository

import com.fnxl.bitflix.core.util.NetworkError
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.data.remote.BitflixApi
import com.fnxl.bitflix.feature_discover.data.remote.StreamLinksRequest
import com.fnxl.bitflix.feature_discover.data.remote.response.StreamLinksResponse
import com.fnxl.bitflix.feature_discover.domain.repository.BitflixRepository

class BitflixRepositoryImpl(private val api: BitflixApi) : BitflixRepository {
    override suspend fun getStreamLinks(request: StreamLinksRequest): Resource<StreamLinksResponse> {
        return try {
            val response = api.streamlinks(requestBody = request)
            Resource.Success(response)
        } catch (e: Exception) {
            NetworkError.resolveError(e)
        }
    }

}
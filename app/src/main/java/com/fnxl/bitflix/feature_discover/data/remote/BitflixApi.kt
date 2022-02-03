package com.fnxl.bitflix.feature_discover.data.remote

import com.fnxl.bitflix.feature_discover.data.remote.response.StreamLinksResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface BitflixApi {
    @POST("media/stream-links")
    suspend fun streamlinks(
        @Body requestBody: StreamLinksRequest
    ): StreamLinksResponse
}
package com.fnxl.bitflix.feature_discover.domain.usecase

import com.fnxl.bitflix.core.domain.models.VideoSource
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.core.util.NetworkError
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_discover.data.remote.StreamLinksRequest
import com.fnxl.bitflix.feature_discover.domain.repository.BitflixRepository

class GetStreamLinksUseCase(private val repository: BitflixRepository) {
    suspend operator fun invoke(
        title: String,
        year: String,
        seasonNumber: Number? = null,
        episodeNumber: Number? = null,
        type: MediaType,
        platform: String = "android"
    ): Resource<Map<String, List<VideoSource>?>> {
        val request = StreamLinksRequest(
            title = title,
            year = year,
            episodeNumber = episodeNumber,
            seasonNumber = seasonNumber,
            type = type.value,
            platform = platform
        )

        val result = try {
            repository.getStreamLinks(request = request)
        } catch (e: Exception) {
            return NetworkError.resolveError(e)
        }
        val map = mapOf(
            "2160p" to result.data?.ultraHD,
            "1080p" to result.data?.fullHD,
            "720p" to result.data?.hd
        )
        return Resource.Success(map)
    }
}
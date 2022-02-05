package com.fnxl.bitflix.core.data.remote

import com.fnxl.bitflix.core.data.remote.response.UpdateResponse
import retrofit2.http.GET

interface UpdateApi {
    @GET("https://api.github.com/repos/fnxl/bitflix-android/releases/latest")
    suspend fun checkUpdate(): UpdateResponse
}
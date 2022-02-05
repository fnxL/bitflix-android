package com.fnxl.bitflix.core.domain.usecase

import com.fnxl.bitflix.core.data.remote.UpdateApi
import com.fnxl.bitflix.core.data.remote.response.UpdateResponse
import com.fnxl.bitflix.core.util.NetworkError
import com.fnxl.bitflix.core.util.Resource

class CheckUpdateUseCase(private val api: UpdateApi) {
     suspend operator fun invoke(): Resource<UpdateResponse> {
         return try {
            val result = api.checkUpdate()
             Resource.Success(result)
         } catch(e: Exception) {
             NetworkError.resolveError(e)
         }
    }
}
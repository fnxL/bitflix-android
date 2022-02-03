package com.fnxl.bitflix.feature_auth.domain.usecase

import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.feature_auth.data.remote.response.TokenResponse
import com.fnxl.bitflix.feature_auth.domain.repository.AuthRepository

class GetTokenUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(refreshToken: String): Resource<TokenResponse> {

        return repository.getToken(refreshToken = refreshToken)
    }
}
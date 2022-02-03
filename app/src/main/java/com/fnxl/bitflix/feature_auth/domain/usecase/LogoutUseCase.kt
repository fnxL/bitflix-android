package com.fnxl.bitflix.feature_auth.domain.usecase

import com.fnxl.bitflix.core.util.SimpleResource
import com.fnxl.bitflix.feature_auth.domain.repository.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(
    ): SimpleResource {
        return  repository.logout()
    }
}
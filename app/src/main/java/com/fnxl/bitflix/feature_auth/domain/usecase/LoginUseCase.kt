package com.fnxl.bitflix.feature_auth.domain.usecase

import com.fnxl.bitflix.core.util.Constants
import com.fnxl.bitflix.core.util.Validation
import com.fnxl.bitflix.feature_auth.domain.model.LoginResult
import com.fnxl.bitflix.feature_auth.domain.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(
        username: String,
        password: String,
    ): LoginResult {

        val usernameError =
            Validation.validateField(text = username, minLength = Constants.MIN_USERNAME_LENGTH)
        val passwordError =
            Validation.validateField(text = password, minLength = Constants.MIN_PASSWORD_LENGTH)

        if (usernameError != null || passwordError != null) {
            return LoginResult(usernameError = usernameError, passwordError = passwordError)
        }

        val result = repository.login(username = username, password = password)

        return LoginResult(result = result)
    }
}
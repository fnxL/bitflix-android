package com.fnxl.bitflix.feature_auth.domain.usecase

import com.fnxl.bitflix.core.util.Constants
import com.fnxl.bitflix.core.util.Validation
import com.fnxl.bitflix.feature_auth.data.remote.request.RegisterRequest
import com.fnxl.bitflix.feature_auth.domain.model.RegisterResult
import com.fnxl.bitflix.feature_auth.domain.repository.AuthRepository

class SignUpUseCase(private val repository: AuthRepository) {

    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String,
        confirmPassword: String,
        inviteKey: String,
    ): RegisterResult {

        val firstNameError =
            Validation.validateField(text = firstName, Constants.MIN_FIRSTNAME_LENGTH)
        val lastNameError = Validation.validateField(text = lastName, Constants.MIN_LASTNAME_LENGTH)
        val usernameError = Validation.validateField(text = username, Constants.MIN_USERNAME_LENGTH)
        val emailError = Validation.validateEmail(email = email)
        val passwordError = Validation.validatePassword(
            password = password,
            confirmPassword = confirmPassword,
            minLength = Constants.MIN_PASSWORD_LENGTH
        )
        val inviteKeyError = Validation.validateInviteKey(inviteKey = inviteKey)

        if (firstNameError != null || lastNameError != null || usernameError != null || emailError != null || passwordError != null || inviteKeyError != null) {
            return RegisterResult(
                firstNameError = firstNameError,
                lastNameError = lastNameError,
                emailError = emailError,
                usernameError = usernameError,
                passwordError = passwordError,
                inviteKeyError = inviteKeyError
            )
        }

        val request = RegisterRequest(
            firstName = firstName.trim(),
            lastName = lastName.trim(),
            email = email.trim(),
            username = username.trim(),
            password = password.trim(),
            inviteKey = inviteKey.trim()
        )

        val result = repository.signup(request = request)
        return RegisterResult(result = result)
    }
}
package com.fnxl.bitflix.core.util

import android.util.Patterns
import com.fnxl.bitflix.feature_auth.presentation.util.AuthError
import java.util.regex.Pattern

object Validation {

    fun validateEmail(email: String): AuthError? {
        val trimmedEmail = email.trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return AuthError.InvalidEmail

        if (trimmedEmail.isBlank()) return AuthError.FieldEmpty

        return null
    }

    fun validatePassword(password: String, confirmPassword: String, minLength: Int = 1): AuthError? {
        val trimmedPassword = password.trim()
        val trimmedConfirmPassword = confirmPassword.trim()
        if (trimmedConfirmPassword.isBlank() || trimmedPassword.isBlank()) {
            return AuthError.FieldEmpty
        }

        if(trimmedPassword != trimmedConfirmPassword) {
            return AuthError.PasswordsDoNotMatch
        }
        if(trimmedConfirmPassword.length < minLength) return AuthError.InputTooShort
        if(trimmedPassword.length < minLength) return AuthError.InputTooShort

        return null
    }

    fun validateField(text: String, minLength: Int = 1): AuthError? {
        val trimmedText = text.trim()
        if (trimmedText.isBlank()) return AuthError.FieldEmpty
        if (trimmedText.length < minLength) return AuthError.InputTooShort
        return null

    }

    fun validateInviteKey(inviteKey: String): AuthError? {
        val trimmedEmail = inviteKey.trim()

        if (!Pattern.matches(
                "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}",
                inviteKey
            )
        ) return AuthError.InvalidInviteKey

        if (trimmedEmail.isBlank()) return AuthError.FieldEmpty
        return null
    }
}
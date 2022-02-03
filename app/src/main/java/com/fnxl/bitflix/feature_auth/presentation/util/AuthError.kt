package com.fnxl.bitflix.feature_auth.presentation.util

import com.fnxl.bitflix.core.util.Error

sealed class AuthError : Error() {
    object FieldEmpty : AuthError()
    object InputTooShort : AuthError()
    object InvalidEmail : AuthError()
    object InvalidInviteKey : AuthError()
    object PasswordsDoNotMatch : AuthError()
}

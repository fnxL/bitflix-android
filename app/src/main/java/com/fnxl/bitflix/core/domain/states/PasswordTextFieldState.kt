package com.fnxl.bitflix.core.domain.states

import com.fnxl.bitflix.core.util.Error

data class PasswordTextFieldState(
    val text: String = "",
    val error: Error? = null,
    val isPasswordVisible: Boolean = false
)

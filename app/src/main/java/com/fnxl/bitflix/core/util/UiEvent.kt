package com.fnxl.bitflix.core.util

import com.ramcosta.composedestinations.spec.Direction


sealed class UiEvent : Event() {
    data class SnackBar(val text: String) : UiEvent()
    data class Toast(val text: String) : UiEvent()
    data class Navigate(val route: Direction) : UiEvent()
    object PopBackStack : UiEvent()
    object OnLogin : UiEvent()


}
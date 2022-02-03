package com.fnxl.bitflix.feature_videoplayer.util

import com.google.accompanist.systemuicontroller.SystemUiController

class ControlSystemUi(private val controller: SystemUiController) {
    fun enableFullscreen() {
        controller.isSystemBarsVisible = true
    }

    fun disableFullscreen() {
        controller.isSystemBarsVisible = true
    }
}
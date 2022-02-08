package com.fnxl.bitflix.core.presentation.splash

sealed class SplashEvent {
    object OnSplashComplete : SplashEvent()
    object OnDownload : SplashEvent()
    object OnInstall : SplashEvent()

}

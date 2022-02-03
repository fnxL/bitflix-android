package com.fnxl.bitflix.feature_profile.presentation.profile

sealed class ProfileEvent {
    object OnBackClick : ProfileEvent()
    object OnLogout : ProfileEvent()
}

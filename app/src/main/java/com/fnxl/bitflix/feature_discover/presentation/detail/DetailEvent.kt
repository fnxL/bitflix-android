package com.fnxl.bitflix.feature_discover.presentation.detail

import com.fnxl.bitflix.core.util.MediaType

sealed class DetailEvent {
    data class OnSeasonChange(val index: Int, val id: String) : DetailEvent()
    object OnBackClick : DetailEvent()
    object OnSeasonDropDownDismiss : DetailEvent()
    object OnSeasonDropDownClick : DetailEvent()
    object OnLinksDialogDismiss : DetailEvent()
    data class OnWatchClick(
        val title: String,
        val year: String,
        val seasonNumber: Number? = null,
        val episodeNumber: Number? = null,
        val type: MediaType,
        val platform: String = "android"
    ) : DetailEvent()

    data class OnQualitySelect(val quality: String) : DetailEvent()
}

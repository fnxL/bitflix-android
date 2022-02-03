package com.fnxl.bitflix.feature_discover.presentation.tvshow

sealed class TVShowEvent {
    data class OnTVShowClick(val id: Int): TVShowEvent()
}

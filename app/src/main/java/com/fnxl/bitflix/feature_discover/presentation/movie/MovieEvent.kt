package com.fnxl.bitflix.feature_discover.presentation.movie

sealed class MovieEvent {
    data class OnMovieClick(val id: Int): MovieEvent()
}

package com.fnxl.bitflix.feature_discover.presentation.search

import com.fnxl.bitflix.core.util.MediaType

sealed class SearchEvent {
    data class OnSearchTextChange(val text: String) : SearchEvent()
    data class OnCloseClick(val text: String) : SearchEvent()
    data class OnVisitTitle(val visited: Boolean, val id: Int, val mediaType: MediaType) :
        SearchEvent()
    object OnSearch : SearchEvent()

}

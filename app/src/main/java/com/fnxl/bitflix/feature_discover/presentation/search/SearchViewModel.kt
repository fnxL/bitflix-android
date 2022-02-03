package com.fnxl.bitflix.feature_discover.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.destinations.DetailScreenDestination
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem
import com.fnxl.bitflix.feature_discover.domain.usecase.DiscoverUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val discoverUseCases: DiscoverUseCases) :
    ViewModel() {


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var searchItem: Flow<PagingData<ResultItem>> = flow {}

    var state by mutableStateOf(SearchState())
        private set


    @ExperimentalComposeUiApi
    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnSearch -> {
                search(state.searchText)
            }
            is SearchEvent.OnSearchTextChange -> {
                state = state.copy(searchText = event.text)
            }
            is SearchEvent.OnVisitTitle -> {
                state = state.copy(titleVisited = event.visited)
                sendUiEvent(UiEvent.Navigate(DetailScreenDestination(event.id, event.mediaType)))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun search(query: String) {
        state = state.copy(loading = true)
        searchItem = discoverUseCases.searchTitleUseCase(query = query)
        state = state.copy(loading = false)
    }
}
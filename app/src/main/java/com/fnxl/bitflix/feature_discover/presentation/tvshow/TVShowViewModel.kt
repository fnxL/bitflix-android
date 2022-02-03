package com.fnxl.bitflix.feature_discover.presentation.tvshow

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnxl.bitflix.core.domain.models.RowData
import com.fnxl.bitflix.core.util.Config.TVShowRowData
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.core.util.sendErrorSnackbar
import com.fnxl.bitflix.destinations.DetailScreenDestination
import com.fnxl.bitflix.feature_discover.data.remote.response.ResultItem
import com.fnxl.bitflix.feature_discover.domain.usecase.DiscoverUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVShowViewModel @Inject constructor(
    private val discoverUseCases: DiscoverUseCases
) : ViewModel() {

    private val mediaType = MediaType.TVShow

    var tvShowRow by mutableStateOf(listOf<Resource<List<ResultItem>>>())
        private set

    var trendingList by mutableStateOf(listOf<ResultItem>())
        private set

    var state by mutableStateOf(TVShowState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getTVShowRowData()
    }


    @ExperimentalComposeUiApi
    fun onEvent(event: TVShowEvent) {
        when (event) {
            is TVShowEvent.OnTVShowClick -> {
                sendUiEvent(UiEvent.Navigate(DetailScreenDestination(id = event.id, mediaType = mediaType)))
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun getTVShowRowData(rowDataList: List<RowData> = TVShowRowData) {
        viewModelScope.launch {
            val results = rowDataList.map { rowData ->
                async {
                    discoverUseCases.getRowData(rowData = rowData, mediaType = mediaType)
                }
            }.awaitAll()

            tvShowRow = results
            getTrendingShows()
        }
    }

    private fun getTrendingShows() {
        viewModelScope.launch {
            when (val result = discoverUseCases.getTrending(mediaType = mediaType)) {
                is Resource.Success -> {
                    trendingList = result.data!!
                }
                is Resource.Error -> {
                    _uiEvent.send(sendErrorSnackbar(result))
                }
            }
            state = state.copy(loading = false)
        }
    }


}
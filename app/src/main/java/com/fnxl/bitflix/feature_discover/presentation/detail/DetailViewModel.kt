package com.fnxl.bitflix.feature_discover.presentation.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fnxl.bitflix.core.util.*
import com.fnxl.bitflix.destinations.PlaybackScreenDestination
import com.fnxl.bitflix.feature_discover.domain.usecase.DiscoverUseCases
import com.fnxl.bitflix.feature_videoplayer.presentation.playback.PlaybackScreenArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val discoverUseCases: DiscoverUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var appendToResponse: String
    private val id = savedStateHandle.get<Int>("id").toString()
    private val mediaType = MediaType.valueOf(savedStateHandle.get<String>("mediaType").toString())

    var state by mutableStateOf(DetailState())

    init {
        appendToResponse = if (mediaType == MediaType.MOVIE)
            Config.APPEND_TO_RESPONSE_MOVIE
        else Config.APPEND_TO_RESPONSE_TV

        getDetails()

    }

    @ExperimentalComposeUiApi
    fun onEvent(event: DetailEvent) {
        when (event) {
            is DetailEvent.OnBackClick -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is DetailEvent.OnSeasonChange -> {
                state = state.copy(selectedSeasonIndex = event.index, seasonDropDown = false)
                getSeasonDetails(
                    id = event.id,
                    state.filteredSeasonList[event.index].season_number.toString()
                )
            }
            is DetailEvent.OnSeasonDropDownDismiss -> {
                state = state.copy(seasonDropDown = false)
            }
            is DetailEvent.OnSeasonDropDownClick -> {
                state = state.copy(seasonDropDown = true)
            }
            is DetailEvent.OnLinksDialogDismiss -> {
                state = state.copy(linksDialog = false)
            }
            is DetailEvent.OnWatchClick -> {
                getStreamlinks(
                    title = event.title,
                    year = event.year,
                    seasonNumber = event.seasonNumber,
                    episodeNumber = event.episodeNumber,
                    type = event.type,
                    platform = event.platform
                )
                state =
                    state.copy(linksDialog = true, linksDialogLoading = true, title = event.title)
            }
            is DetailEvent.OnQualitySelect -> {
                val args =
                    state.streamLinks[event.quality]?.let {
                        PlaybackScreenArgs(
                            title = state.title,
                            videoSourceList = it
                        )
                    }

                args?.let {
                    sendUiEvent(UiEvent.Navigate(PlaybackScreenDestination(args = args)))
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }

    private fun getStreamlinks(
        title: String,
        year: String,
        seasonNumber: Number? = null,
        episodeNumber: Number? = null,
        type: MediaType,
        platform: String = "android"
    ) {

        viewModelScope.launch {
            when (val result = discoverUseCases.getStreamLinksUseCase(
                title = title,
                year = year,
                seasonNumber = seasonNumber,
                episodeNumber = episodeNumber,
                type = type,
                platform = platform
            )) {
                is Resource.Success -> {
                    state = state.copy(streamLinks = result.data!!, linksDialogLoading = false)
                }
                is Resource.Error -> {
                    _uiEvent.send(sendErrorSnackbar(result))
                    state = state.copy(linksDialog = false, linksDialogLoading = false)
                }
            }
        }
    }


    private fun getDetails() {
        viewModelScope.launch {
            when (mediaType) {
                MediaType.MOVIE -> {
                    state = when (val result =
                        discoverUseCases.getMovieDetails(id = id, append = appendToResponse)) {
                        is Resource.Success -> {
                            state.copy(movie = result.data!!, loading = false)
                        }
                        is Resource.Error -> {
                            _uiEvent.send(sendErrorSnackbar(result))
                            state.copy(loading = true)
                        }
                    }
                }
                MediaType.TVShow -> {
                    state = when (val result =
                        discoverUseCases.getShowDetails(id = id, append = appendToResponse)) {
                        is Resource.Success -> {
                            val filterSeasons =
                                result.data!!.seasons.filter { it.season_number != 0 }
                            getSeasonDetails(id = result.data.id.toString(), seasonNumber = "1")
                            state.copy(
                                tvShow = result.data,
                                filteredSeasonList = filterSeasons,
                                loading = false
                            )
                        }
                        is Resource.Error -> {
                            _uiEvent.send(sendErrorSnackbar(result))
                            state.copy(loading = true)
                        }
                    }
                }
            }
        }
    }

    private fun getSeasonDetails(id: String, seasonNumber: String) {
        viewModelScope.launch {
            state = when (val result =
                discoverUseCases.getSeasonDetails(id = id, seasonNumber = seasonNumber)) {
                is Resource.Success -> {
                    state.copy(seasonDetails = result.data!!, loading = false)
                }
                is Resource.Error -> {
                    _uiEvent.send(sendErrorSnackbar(result))
                    state.copy(loading = false)
                }
            }
        }
    }
}



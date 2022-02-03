package com.fnxl.bitflix.feature_discover.presentation.movie

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.fnxl.bitflix.LocalSnackbar
import com.fnxl.bitflix.core.presentation.components.StandardProgressIndicator
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.feature_discover.presentation.components.Carousel
import com.fnxl.bitflix.feature_discover.presentation.components.TitleRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Destination
@Composable
fun MovieScreen(
    navigator: DestinationsNavigator,
    viewModel: MovieViewModel = hiltViewModel()
) {
    val movieRow = viewModel.movieRow
    val trendingList = viewModel.trendingList
    val state = viewModel.state
    val snackbar = LocalSnackbar.current
    val context = LocalContext.current



    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.SnackBar -> snackbar.showSnackbar(event.text)
                is UiEvent.Navigate -> navigator.navigate(event.route)
                else -> Unit
            }
        }
    }
    if (state.loading) {
        StandardProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(
            state = rememberLazyListState(),
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    movieRow[0].data?.let {
                        Carousel(
                            list = it.slice(1..5),
                            onClick = { id ->
                                viewModel.onEvent(MovieEvent.OnMovieClick(id))
                            }
                        )
                    }
                }
                // Trending Movies List
                Row(Modifier.padding(SpaceMedium)) {
                    TitleRow(
                        title = "Trending Now",
                        list = trendingList,
                        onClick = { id, _ ->
                            viewModel.onEvent(MovieEvent.OnMovieClick(id))
                        },
                    )
                }

            }

            itemsIndexed(movieRow) { index, item ->
                Column(Modifier.padding(SpaceMedium)) {
                    TitleRow(
                        title = item.title!!,
                        list = item.data!!,
                        onClick = { id, _ ->
                            viewModel.onEvent(MovieEvent.OnMovieClick(id))
                        }

                    )
                }
            }
        }
    }
}
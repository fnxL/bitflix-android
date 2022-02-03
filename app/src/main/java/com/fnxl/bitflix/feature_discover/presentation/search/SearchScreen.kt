package com.fnxl.bitflix.feature_discover.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.fnxl.bitflix.core.presentation.components.SpacerMedium
import com.fnxl.bitflix.core.presentation.components.StandardProgressIndicator
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceSmall
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.feature_discover.presentation.components.SearchBar
import com.fnxl.bitflix.feature_discover.presentation.components.TitleRowPoster
import com.fnxl.bitflix.feature_discover.presentation.util.items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Destination
@Composable
fun SearchScreen(viewModel: SearchViewModel = hiltViewModel(), navigator: DestinationsNavigator) {
    val state = viewModel.state

    val gridState = rememberLazyListState()

    val focusManager = LocalFocusManager.current

    val searchItems = viewModel.searchItem.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true, block = {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navigator.navigate(event.route)
                }
                else -> Unit
            }
        }
    })

    Column {
        SearchBar(
            text = state.searchText,
            onTextChange = {
                viewModel.onEvent(SearchEvent.OnSearchTextChange(it))
            },
            onCloseClicked = {
                viewModel.onEvent(SearchEvent.OnCloseClick(""))
            },
            onSearchClicked = {
                viewModel.onEvent(SearchEvent.OnSearch)
                focusManager.clearFocus()
            },
            clickedState = state.titleVisited
        )
        SpacerMedium()
        Column {
            Column(Modifier.padding(horizontal = SpaceMedium)) {

                if (searchItems.itemCount > 0) {
                    Text(
                        modifier = Modifier.padding(bottom = SpaceSmall),
                        text = "Movies & TV Shows",
                        fontWeight = FontWeight.Medium
                    )
                }

            }
            LazyVerticalGrid(
                state = gridState,
                cells = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(
                    SpaceMedium
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    SpaceMedium,
                    Alignment.CenterHorizontally
                ),
                modifier = Modifier.padding(SpaceMedium),
            ) {

                items(searchItems) { item ->
                    TitleRowPoster(
                        item = item, onClick = { id, mediaType ->
                            viewModel.onEvent(
                                SearchEvent.OnVisitTitle(
                                    visited = true,
                                    id = id,
                                    mediaType = mediaType!!
                                )
                            )
                        }
                    )
                }

            }
        }

        if (state.loading)
            StandardProgressIndicator(modifier = Modifier.fillMaxSize())


        if (state.error.isNotEmpty()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 210.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Some unknown error occurred")

            }
        }

    }

}
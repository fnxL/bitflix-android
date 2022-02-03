package com.fnxl.bitflix.feature_discover.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fnxl.bitflix.LocalSnackbar
import com.fnxl.bitflix.core.presentation.components.StandardAppDialog
import com.fnxl.bitflix.core.presentation.components.StandardProgressIndicator
import com.fnxl.bitflix.core.presentation.components.StandardTextButton
import com.fnxl.bitflix.core.presentation.ui.theme.RedAccent
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.core.util.MediaType
import com.fnxl.bitflix.core.util.UiEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@Destination(navArgsDelegate = DetailScreenNavArgs::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    navArgsDelegate: DetailScreenNavArgs
) {
    val snackBar = LocalSnackbar.current
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    navigator.navigate(event.route)
                }
                is UiEvent.SnackBar -> {
                    snackBar.showSnackbar(event.text)
                }
                is UiEvent.PopBackStack -> {
                    navigator.popBackStack()
                }
                else -> Unit
            }
        }
    }
    if (state.loading) {
        StandardProgressIndicator(modifier = Modifier.fillMaxSize())
    } else {
        when (navArgsDelegate.mediaType) {
            MediaType.MOVIE -> MovieDetails(state = state, onEvent = viewModel::onEvent)
            MediaType.TVShow -> TVShowDetails(state = state, onEvent = viewModel::onEvent)
        }

        StandardAppDialog(
            modifier = Modifier.size(200.dp),
            dialogState = state.linksDialog,
            onDismissRequest = { viewModel.onEvent(DetailEvent.OnLinksDialogDismiss) }
        ) {

            var linksFound by remember {
                mutableStateOf(false)
            }
            if (state.linksDialogLoading) {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = RedAccent,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(36.dp)
                    )
                }
            } else {
                Row(
                    Modifier
                        .padding(SpaceMedium)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Select Quality",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    for ((key, value) in state.streamLinks) {
                        if (value?.isNotEmpty() == true) {
                            linksFound = true
                            StandardTextButton(
                                text = key,
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    backgroundColor = Color.Transparent
                                ),
                                onClick = {
                                    viewModel.onEvent(DetailEvent.OnQualitySelect(quality = key))
                                }
                            )
                        }
                    }
                    if (!linksFound) {
                        Text(text = "No Links Found")
                    }
                }

            }
        }
    }
}
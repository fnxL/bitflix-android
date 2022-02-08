package com.fnxl.bitflix.core.presentation.splash


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fnxl.bitflix.R
import com.fnxl.bitflix.core.presentation.components.SpacerSmall
import com.fnxl.bitflix.core.presentation.components.StandardAppDialog
import com.fnxl.bitflix.core.presentation.ui.theme.DarkGray
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerMedium
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium
import com.fnxl.bitflix.core.util.Constants
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.destinations.SplashScreenDestination
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Destination(start = true)
@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {

    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = Constants.SPLASH_SCREEN_DURATION.toInt())

    )
    val state = viewModel.state

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.PopBackStack -> {
                    navigator.popBackStack()
                }
                is UiEvent.Navigate -> {
                    navigator.navigate(event.route) {
                        popUpTo(SplashScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(Constants.SPLASH_SCREEN_DURATION)
        viewModel.onEvent(SplashEvent.OnSplashComplete)
    }

    Splash(alpha = alphaAnim.value)

    if (viewModel.updateDialog) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            backgroundColor = DarkGray,
            shape = RoundedCornerShape(RoundedCornerMedium),
            title = {
                Text(text = "Update Available")
            },
            text = {
                Column {
                    Text(text = "A new update for Bitflix is available")
                    SpacerSmall()
                    Text(text = "What's new", fontWeight = FontWeight.Bold)
                    SpacerSmall()
                    Text(text = state.update.body)
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (viewModel.fileExists) viewModel.onEvent(SplashEvent.OnInstall) else viewModel.onEvent(SplashEvent.OnDownload)
                }) {
                    Text(text = if (viewModel.fileExists) "Install" else "Download")
                }
            },
        )
    }


}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .alpha(alpha),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "Logo Icon",
        )
    }
}

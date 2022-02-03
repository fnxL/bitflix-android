package com.fnxl.bitflix.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fnxl.bitflix.core.presentation.ui.theme.MediumGray
import com.fnxl.bitflix.destinations.PlaybackScreenDestination
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun StandardScaffold(
    modifier: Modifier = Modifier,
    state: ScaffoldState,
    navController: NavHostController,
    bottomBarState: Boolean,
    content: @Composable () -> Unit
) {

    val currentRoute = navController.currentBackStackEntry?.destination?.route
    Scaffold(
        scaffoldState = state,
        snackbarHost = {
            SnackbarHost(it) { snackbarData ->
                Snackbar(
                    backgroundColor = MediumGray,
                    contentColor = Color.White,
                    snackbarData = snackbarData
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController, state = bottomBarState)
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            content()
        }
    }
}
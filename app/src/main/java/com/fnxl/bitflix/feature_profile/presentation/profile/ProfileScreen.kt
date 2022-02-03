package com.fnxl.bitflix.feature_profile.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fnxl.bitflix.LocalSnackbar
import com.fnxl.bitflix.core.presentation.components.StandardTextButton
import com.fnxl.bitflix.core.util.UiEvent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect


@Composable
@Destination
fun ProfileScreen(navigator: DestinationsNavigator, viewModel: ProfileViewModel = hiltViewModel()) {
    val snackBar = LocalSnackbar.current

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
    Column {
        ProfileTopBar(onBackClick = { viewModel.onEvent(ProfileEvent.OnBackClick) })
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            StandardTextButton(
                text = "Account",
                icon = Icons.Default.AccountBox,
                onClick = {},
            )
            StandardTextButton(
                text = "Logout",
                icon = Icons.Default.Logout,
                onClick = {
                    viewModel.onEvent(ProfileEvent.OnLogout)
                },
            )
        }
    }
}
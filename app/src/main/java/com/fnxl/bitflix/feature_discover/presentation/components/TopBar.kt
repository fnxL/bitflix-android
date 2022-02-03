package com.fnxl.bitflix.feature_discover.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fnxl.bitflix.R
import com.fnxl.bitflix.core.presentation.components.SpacerSmall
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceSmall
import com.fnxl.bitflix.destinations.ProfileScreenDestination
import com.fnxl.bitflix.destinations.SearchScreenDestination


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@Composable
fun TopBar(navController: NavController, state: Boolean) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = state,
        enter = slideInVertically {
            with(density) {
                -40.dp.roundToPx()
            }
        },
        exit = slideOutVertically() + shrinkVertically(),
    ) {
        TopAppBar(
            title = {
                Box(modifier = Modifier.padding(start = 8.dp)) {
                    Image(
                        modifier = Modifier.size(52.dp),
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = ""
                    )
                }

            },
            elevation = 0.dp,
            contentColor = Color.White,
            backgroundColor = Color.Transparent,
            actions = {
                Row(Modifier.padding(horizontal = 18.dp, vertical = 2.dp)) {
                    SearchAction(onClick = {
                        navController.navigate(SearchScreenDestination.route) {
                            launchSingleTop = true
                        }
                    })
                    SpacerSmall()
                    ProfileIcon(
                        onClick = {
                            navController.navigate(ProfileScreenDestination.route) {
                                launchSingleTop = true
                            }
                        })
                }

            },

            )
    }

}

@Composable
fun SearchAction(
    onClick: () -> Unit
) {
    IconButton(onClick = { onClick() }) {
        Icon(
            modifier = Modifier.alpha(ContentAlpha.high),
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = Color.White
        )
    }
}

@Composable
fun ProfileIcon(onClick: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.ic_baseprofile),
        contentDescription = "",
        modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}


package com.fnxl.bitflix.core.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fnxl.bitflix.core.presentation.ui.theme.DarkGray
import com.fnxl.bitflix.core.presentation.ui.theme.NoRippleTheme
import com.fnxl.bitflix.core.util.Config.bottomNavItems
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    state: Boolean
) {

    AnimatedVisibility(
        visible = state,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        CompositionLocalProvider(
            LocalRippleTheme provides NoRippleTheme,
            LocalElevationOverlay provides null
        ) {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination?.route
            BottomNavigation(backgroundColor = DarkGray) {
                bottomNavItems.forEach { item ->
                    StandardBottomNavItem(
                        selected = item.route == currentRoute,
                        icon = item.icon,
                        label = item.title
                    ) {
                        navController.navigate(item.route) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            }
        }
    }
}

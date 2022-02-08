package com.fnxl.bitflix

import android.Manifest
import android.app.UiModeManager
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.fnxl.bitflix.core.domain.DataStorePreferences
import com.fnxl.bitflix.core.presentation.components.StandardScaffold
import com.fnxl.bitflix.core.presentation.splash.SplashScreen
import com.fnxl.bitflix.core.presentation.ui.theme.BitflixMobileTheme
import com.fnxl.bitflix.core.presentation.ui.theme.BitflixTVTheme
import com.fnxl.bitflix.core.presentation.ui.theme.DarkGray
import com.fnxl.bitflix.core.util.Resource
import com.fnxl.bitflix.destinations.*
import com.fnxl.bitflix.feature_auth.domain.usecase.CheckSessionUseCase
import com.fnxl.bitflix.feature_discover.presentation.components.TopBar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.properties.Delegates


val LocalSnackbar = compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState Provided") }

@AndroidEntryPoint
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStorePref: DataStorePreferences

    @Inject
    lateinit var checkSessionUseCase: CheckSessionUseCase

    private var shouldExecuteOnResume by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Install permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!getPackageManager().canRequestPackageInstalls()) {
                startActivityForResult(
                    Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                        .setData(Uri.parse(String.format("package:%s", getPackageName()))), 1
                );
            }
        }

        //Storage Permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1
            )
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }

        shouldExecuteOnResume = false
        setContent {
            val uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
            // Check Device Type

            if (uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION) {
                BitflixTVTheme {

                }
            } else {
                val scaffoldState = rememberScaffoldState()
                val navController = rememberNavController()
                val backStack by navController.currentBackStackEntryAsState()
                val currentRoute = backStack?.destination?.route
                val originalSystemView by rememberSaveable { mutableStateOf(window.decorView.systemUiVisibility) }
                val systemUi = rememberSystemUiController()
                if (currentRoute == PlaybackScreenDestination.route) hideSystemUI() else showSystemUI(
                    originalSystemView = originalSystemView
                )

                SideEffect {
                    systemUi.setSystemBarsColor(DarkGray)
                }

                BitflixMobileTheme {
                    CompositionLocalProvider(LocalSnackbar provides scaffoldState.snackbarHostState) {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            StandardScaffold(
                                state = scaffoldState,
                                navController = navController,
                                bottomBarState = shouldShowAppBars(currentRoute = currentRoute),
                            ) {
                                DestinationsNavHost(
                                    navGraph = NavGraphs.root,
                                    navController = navController
                                ) {
                                    composable(SplashScreenDestination) {
                                        SplashScreen(navigator = destinationsNavigator)
                                    }
                                }
                                TopBar(
                                    navController = navController,
                                    state = shouldShowAppBars(currentRoute = currentRoute)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun shouldShowAppBars(currentRoute: String?): Boolean {
        return currentRoute in listOf(
            MovieScreenDestination.route,
            TVShowScreenDestination.route,
            SearchScreenDestination.route,
        )
    }

    override fun onResume() {
        super.onResume()
        if (shouldExecuteOnResume)
            checkUserLoginActivity()
        else shouldExecuteOnResume = true
    }


    private fun checkUserLoginActivity() {
        lifecycleScope.launch {
            val refreshToken = dataStorePref.readRefreshToken().first()
            if (refreshToken.isNotEmpty()) {
                Timber.d("ROUTE: ON RESUME API CHECK TRIGGERED")
                when (val result = checkSessionUseCase(refreshToken = refreshToken)) {
                    is Resource.Success -> {
                        // do nothing
                    }
                    is Resource.Error -> {
                        // Logout
                        if (result.exception?.errorCode != -1) {
                            dataStorePref.saveRefreshToken("")
                            dataStorePref.saveAccessToken("")
                        }
                    }
                }
            }
        }
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        val decorView = window.decorView
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
        )
    }

    // Shows the system bars by removing all the flags
    private fun showSystemUI(originalSystemView: Int) {
        window.decorView.systemUiVisibility = originalSystemView

    }


}

package com.fnxl.bitflix.core.presentation.components

import android.app.Activity
import android.view.View
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import com.fnxl.bitflix.core.presentation.ui.theme.MediumGray
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerMedium

@ExperimentalComposeUiApi
@Composable
fun StandardAppDialog(
    modifier: Modifier = Modifier,
    dialogState: Boolean,
    shape: Shape = RoundedCornerShape(RoundedCornerMedium),
    backgroundColor: Color = MediumGray,
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        usePlatformDefaultWidth = false
    ),
    onDismissRequest: () -> Unit,
    isImmersiveMode: Boolean = false,
    content: @Composable () -> Unit,
) {

    if (dialogState) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            shape = shape,
            backgroundColor = backgroundColor,
            properties = properties,
            buttons = { content() },
        )

    } else {
        if (isImmersiveMode) HideSystemUI()
    }
}

@Composable
fun HideSystemUI() {
    val activity = LocalContext.current as Activity
    val decorView = activity.window.decorView
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
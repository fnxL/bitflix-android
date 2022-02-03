package com.fnxl.bitflix.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium

@Composable
fun ColumnScope.SpacerMedium() {
    Spacer(modifier = Modifier.height(SpaceMedium))
}

@Composable
fun RowScope.SpacerMedium() {
    Spacer(modifier = Modifier.width(SpaceMedium))
}
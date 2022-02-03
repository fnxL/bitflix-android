package com.fnxl.bitflix.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceSmall

@Composable
fun ColumnScope.SpacerSmall() {
    Spacer(modifier = Modifier.height(SpaceSmall))
}

@Composable
fun RowScope.SpacerSmall() {
    Spacer(modifier = Modifier.width(SpaceSmall))
}
package com.fnxl.bitflix.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.fnxl.bitflix.core.presentation.ui.theme.MediumGray
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerMedium

@Composable
fun StandardDropDownMenu(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    isImmersive: Boolean = false,
    backgroundColor: Color = MediumGray,
    anchor: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Box {
        anchor()
        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(
                medium = RoundedCornerShape(
                    RoundedCornerMedium
                )
            )
        ) {
            DropdownMenu(
                modifier = Modifier
                    .background(backgroundColor)
                    .then(modifier),
                expanded = expanded,
                onDismissRequest = onDismissRequest,
            ) {
                content()
            }
            if (isImmersive && !expanded) HideSystemUI()
        }
    }
}

@Composable
fun StandardDropDownMenuItem(
    onClick: () -> Unit,
    text: String,
) {

    DropdownMenuItem(onClick = onClick) {
        Text(
            text = text, textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
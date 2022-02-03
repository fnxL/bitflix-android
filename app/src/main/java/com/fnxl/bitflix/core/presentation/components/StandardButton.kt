package com.fnxl.bitflix.core.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerLarge

@Composable
fun StandardButton(
    modifier: Modifier = Modifier,
    text: String,
    shape: RoundedCornerShape = RoundedCornerShape(RoundedCornerLarge),
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = contentPadding,
        shape = shape,
        enabled = enabled,
        colors = colors,

        ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onPrimary
        )
    }

}


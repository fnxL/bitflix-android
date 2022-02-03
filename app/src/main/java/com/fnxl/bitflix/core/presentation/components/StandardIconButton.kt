package com.fnxl.bitflix.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun StandardIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    backgroundColor: Color = Color.Transparent,
    alpha: Float = 1f,
    shape: Shape = CircleShape,
    iconModifier: Modifier = Modifier,
    contentDescription: String,
    onClick: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .background(backgroundColor.copy(alpha), shape = shape)
            .then(modifier),
        onClick = onClick
    ) {
        Icon(modifier=iconModifier,imageVector = icon, contentDescription = contentDescription)
    }
}
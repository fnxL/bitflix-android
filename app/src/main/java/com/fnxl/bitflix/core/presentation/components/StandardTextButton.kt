package com.fnxl.bitflix.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerMedium

@Composable
fun StandardTextButton(
    modifier: Modifier = Modifier,
    text: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Transparent,
        contentColor = Color.White
    ),
    shape: Shape = RoundedCornerShape(RoundedCornerMedium),
    enabled: Boolean = true,
    icon: ImageVector? = null,
    style: TextStyle = MaterialTheme.typography.button,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        enabled = enabled,
        colors = colors
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null)
                SpacerSmall()
            }
            Text(text = text, style = style)
        }

    }
}

@Composable
fun StandardTextButton(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = Color.Transparent,
        contentColor = Color.White
    ),
    shape: Shape = RoundedCornerShape(RoundedCornerMedium),
    enabled: Boolean = true,
    style: TextStyle = MaterialTheme.typography.button,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        enabled = enabled,
        colors = colors
    ) {
        Text(text = text, style = style)
    }
}
package com.fnxl.bitflix.feature_discover.presentation.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun Overview(text: String, maxLine: Int) {

    var expanded by remember { mutableStateOf(false) }

    Text(
        text = text,
        maxLines = if (expanded) Int.MAX_VALUE else maxLine,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .clickable { expanded = !expanded }
            .alpha(ContentAlpha.medium)
    )

}

package com.fnxl.bitflix.feature_discover.presentation.detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.core.presentation.ui.theme.ChipGray

@Composable
fun GenreChip(name: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = ChipGray.copy(0.7f),
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp, end = 12.dp, top = 2.dp),
            text = name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
        )
    }
}
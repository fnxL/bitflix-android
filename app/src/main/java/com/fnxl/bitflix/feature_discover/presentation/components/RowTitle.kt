package com.fnxl.bitflix.feature_discover.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerMedium

@Composable
fun RowTitle(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(RoundedCornerMedium))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = title, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.width(1.dp))
        Icon(
            modifier = Modifier.padding(top = 3.dp),
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "More",
        )
    }
}
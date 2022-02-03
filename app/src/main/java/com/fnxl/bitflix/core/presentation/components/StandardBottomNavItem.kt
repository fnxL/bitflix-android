package com.fnxl.bitflix.core.presentation.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.fnxl.bitflix.core.presentation.ui.theme.HintGray

@Composable
fun RowScope.StandardBottomNavItem(
    modifier: Modifier = Modifier,
    selected: Boolean,
    icon: ImageVector,
    contentDescription: String? = null,
    selectedColor: Color = MaterialTheme.colors.primary,
    unselectedColor: Color = HintGray,
    enabled: Boolean = true,
    label: String,
    onClick: () -> Unit
) {
    BottomNavigationItem(
        modifier = modifier,
        selected = selected,
        onClick = onClick,
        selectedContentColor = selectedColor,
        unselectedContentColor = unselectedColor,
        enabled = enabled,
        label = {
            Text(text = label)
        },
        alwaysShowLabel = false,
        icon = {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                imageVector = icon,
                contentDescription = contentDescription
            )
        },
    )
}
package com.fnxl.bitflix.feature_profile.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.core.presentation.components.StandardIconButton
import com.fnxl.bitflix.core.presentation.ui.theme.DarkGray
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium

@Composable
fun ProfileTopBar(onBackClick: () -> Unit) {

    Row(
        modifier = Modifier
            .padding(SpaceMedium)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Box(modifier = Modifier.fillMaxWidth()) {
            StandardIconButton(backgroundColor = DarkGray,icon = Icons.Default.ArrowBack, contentDescription = "Back") {
                onBackClick()
            }
            Text(
                text = "Profile", fontWeight = FontWeight.Medium, modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
    }

}
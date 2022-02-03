package com.fnxl.bitflix.feature_discover.presentation.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.fnxl.bitflix.core.presentation.components.StandardIconButton
import com.fnxl.bitflix.core.presentation.ui.theme.ChipGray
import com.fnxl.bitflix.core.presentation.ui.theme.RoundedCornerMediumSmall
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceMedium

@Composable
fun DetailTopBar(onBackClicked: () -> Unit) {
    Row(
        Modifier
            .padding(SpaceMedium)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back Button

        StandardIconButton(
            onClick = { onBackClicked() },
            icon = Icons.Default.ArrowBack,
            contentDescription = "Back",
            backgroundColor = ChipGray,
            alpha = 0.7f,
            shape = RoundedCornerShape(RoundedCornerMediumSmall)
        )

        //Add to List

        StandardIconButton(
            onClick = { },
            icon = Icons.Default.Add,
            contentDescription = "Add to List",
            backgroundColor = ChipGray,
            alpha = 0.7f,
            shape = RoundedCornerShape(RoundedCornerMediumSmall)
        )
    }
}
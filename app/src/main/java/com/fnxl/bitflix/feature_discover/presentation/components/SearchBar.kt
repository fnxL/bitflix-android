package com.fnxl.bitflix.feature_discover.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.R
import com.fnxl.bitflix.core.presentation.components.StandardTextField
import com.fnxl.bitflix.core.presentation.components.StandardTextFieldPainter
import com.fnxl.bitflix.feature_discover.presentation.util.TrailingIconState

@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    clickedState: Boolean
) {
    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_CLOSE)
    }
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .padding(top = 80.dp, start = 16.dp, end = 16.dp)
            .height(56.dp),
        color = Color.Transparent
    ) {
        val focusRequester = FocusRequester()

        DisposableEffect(Unit) {
            if (!clickedState) {
                focusRequester.requestFocus()
            }
            onDispose { }
        }

        StandardTextFieldPainter(
            focusRequester = focusRequester,
            text = text,
            keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
            onValueChange = {
                onTextChange(it)
            },
            placeholder = "Search",
            style = TextStyle(
                color = Color.White,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            leadingIcon = painterResource(id = R.drawable.ic_search),
            trailingIcon = painterResource(id = R.drawable.ic_close),
            imeAction = ImeAction.Search,
            onTrailingIconClick = {
                when (trailingIconState) {
                    TrailingIconState.READY_TO_DELETE -> {
                        onTextChange("")
                        trailingIconState = TrailingIconState.READY_TO_CLOSE
                    }
                    TrailingIconState.READY_TO_CLOSE -> {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                            focusManager.clearFocus()
                            trailingIconState = TrailingIconState.READY_TO_DELETE
                        }
                    }
                }
            }
        )
    }

}
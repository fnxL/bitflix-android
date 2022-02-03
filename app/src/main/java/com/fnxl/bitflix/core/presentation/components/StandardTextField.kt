package com.fnxl.bitflix.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fnxl.bitflix.core.presentation.ui.theme.*

@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = "",
    error: String = "",
    style: TextStyle = TextStyle(
        color = MaterialTheme.colors.onBackground
    ),
    shape: RoundedCornerShape = RoundedCornerShape(RoundedCornerLarge),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        backgroundColor = MediumGray,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = MaterialTheme.colors.primary,
        focusedLabelColor = Color.White,
        errorIndicatorColor = Color.Transparent
    ),
    singleLine: Boolean = true,
    maxLines: Int = 1,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = keyboardType,
        imeAction = imeAction
    ),
    keyboardActions: KeyboardActions,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: () -> Unit = {},
    focusRequester: FocusRequester = FocusRequester()
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .then(modifier),
            value = text,
            onValueChange = onValueChange,
            maxLines = maxLines,
            textStyle = style,
            colors = colors,
            placeholder = {
                Text(
                    text = placeholder, style = MaterialTheme.typography.body1
                )
            },
            isError = error != "",
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            singleLine = singleLine,
            leadingIcon = if (leadingIcon != null) {
                val icon: @Composable () -> Unit = {
                    Icon(
                        modifier = Modifier
                            .alpha(ContentAlpha.disabled),
                        imageVector = leadingIcon,
                        contentDescription = null,
                    )
                }
                icon
            } else null,
            shape = shape,
            trailingIcon = {
                val icon =
                    if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                if (isPasswordToggleDisplayed) {
                    IconButton(onClick = { onPasswordToggleClick(!isPasswordVisible) }) {
                        Icon(imageVector = icon, contentDescription = "Toggle Password Visibility")
                    }
                }
                if (trailingIcon != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(imageVector = trailingIcon, contentDescription = null)
                    }
                }

            }
        )
        if (error.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = error,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Composable
fun StandardTextFieldPainter(
    modifier: Modifier = Modifier,
    text: String = "",
    placeholder: String = "",
    error: String = "",
    style: TextStyle = TextStyle(
        color = MaterialTheme.colors.onBackground
    ),
    shape: RoundedCornerShape = RoundedCornerShape(RoundedCornerLarge),
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = Color.Transparent,
        backgroundColor = MediumGray,
        unfocusedIndicatorColor = Color.Transparent,
        cursorColor = MaterialTheme.colors.primary,
        focusedLabelColor = Color.White,
        errorIndicatorColor = Color.Transparent
    ),
    singleLine: Boolean = true,
    maxLines: Int = 1,
    leadingIcon: Painter? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        keyboardType = keyboardType,
        imeAction = imeAction
    ),
    keyboardActions: KeyboardActions,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onValueChange: (String) -> Unit,
    trailingIcon: Painter? = null,
    onTrailingIconClick: () -> Unit = {},
    focusRequester: FocusRequester = FocusRequester()
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .then(modifier),
            value = text,
            onValueChange = onValueChange,
            maxLines = maxLines,
            textStyle = style,
            colors = colors,
            placeholder = {
                Text(
                    text = placeholder, style = MaterialTheme.typography.body1
                )
            },
            isError = error != "",
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            singleLine = singleLine,
            leadingIcon = if (leadingIcon != null) {
                val icon: @Composable () -> Unit = {
                    Icon(
                        modifier = Modifier
                            .alpha(ContentAlpha.high)
                            .padding(start = SpaceSmall),
                        painter = leadingIcon,
                        tint = RedAccent,
                        contentDescription = null,
                    )
                }
                icon
            } else null,
            shape = shape,
            trailingIcon = {
                val icon =
                    if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility
                if (isPasswordToggleDisplayed) {
                    IconButton(onClick = { onPasswordToggleClick(!isPasswordVisible) }) {
                        Icon(imageVector = icon, contentDescription = "Toggle Password Visibility")
                    }
                }
                if (trailingIcon != null) {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(painter = trailingIcon, contentDescription = null)
                    }
                }

            }
        )
        if (error.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = error,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.End,
            )
        }
    }
}
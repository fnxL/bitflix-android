package com.fnxl.bitflix.feature_auth.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fnxl.bitflix.LocalSnackbar
import com.fnxl.bitflix.R
import com.fnxl.bitflix.core.presentation.components.SpacerMedium
import com.fnxl.bitflix.core.presentation.components.StandardButton
import com.fnxl.bitflix.core.presentation.components.StandardTextButton
import com.fnxl.bitflix.core.presentation.components.StandardTextField
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceLarge
import com.fnxl.bitflix.core.util.Constants
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.destinations.LoginScreenDestination
import com.fnxl.bitflix.destinations.MovieScreenDestination
import com.fnxl.bitflix.feature_auth.presentation.util.AuthError
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Destination
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {

    val username = viewModel.username
    val password = viewModel.password
    val state = viewModel.state
    val snackBar = LocalSnackbar.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.SnackBar -> {
                    snackBar.showSnackbar(event.text)
                }
                is UiEvent.Navigate -> {
                    navigator.navigate(event.route) {
                        launchSingleTop = true
                    }
                }
                is UiEvent.OnLogin -> {
                    navigator.navigate(MovieScreenDestination) {
                        popUpTo(LoginScreenDestination.route) {
                            inclusive = true
                        }
                    }
                }
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SpaceLarge,
                end = SpaceLarge,
                top = SpaceLarge,
                bottom = 60.dp
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Start
            )
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = "Pickup where you left off",
                style = MaterialTheme.typography.subtitle2,
                color = Color.White
            )

            SpacerMedium()

            StandardTextField(
                text = username.text,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.OnUsernameChange(it))
                },
                error = when (username.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    is AuthError.InputTooShort -> stringResource(
                        id = R.string.error_input_short,
                        Constants.MIN_USERNAME_LENGTH
                    )
                    else -> ""
                },
                placeholder = stringResource(id = R.string.username),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                })
            )

            SpacerMedium()

            StandardTextField(
                text = password.text,
                onValueChange = {
                    viewModel.onEvent(LoginEvent.OnPasswordChange(it))
                },
                error = when (password.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    is AuthError.InputTooShort -> stringResource(
                        id = R.string.error_input_short,
                        Constants.MIN_PASSWORD_LENGTH
                    )
                    else -> ""
                },
                placeholder = stringResource(id = R.string.password),
                isPasswordVisible = state.isPasswordVisible,
                onPasswordToggleClick = {
                    viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                },
                keyboardType = KeyboardType.Password,
                keyboardActions = KeyboardActions(onGo = {
                    viewModel.onEvent(LoginEvent.Login)
                    focusManager.clearFocus()
                }),
                imeAction = ImeAction.Go
            )

            SpacerMedium()

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                StandardTextButton(
                    text = "Forgot Password?",
                    onClick = {},
                    style = MaterialTheme.typography.body1
                )

                StandardButton(
                    text = stringResource(id = R.string.login),
                    onClick = {
                        viewModel.onEvent(LoginEvent.Login)
                        focusManager.clearFocus()
                    },
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                )
            }
            if (state.loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }

        StandardTextButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = buildAnnotatedString {
                append(stringResource(id = R.string.dont_have_an_account_yet))
                append(" ")
                val signUpText = stringResource(id = R.string.sign_up)
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.primary
                    )
                ) {
                    append(signUpText)
                }
            },
            onClick = {
                viewModel.onEvent(LoginEvent.OnSignUpClick)
            },
            style = MaterialTheme.typography.body1
        )

    }


}
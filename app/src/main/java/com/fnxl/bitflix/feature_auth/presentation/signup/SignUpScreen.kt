package com.fnxl.bitflix.feature_auth.presentation.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fnxl.bitflix.LocalSnackbar
import com.fnxl.bitflix.R
import com.fnxl.bitflix.core.presentation.components.*
import com.fnxl.bitflix.core.presentation.ui.theme.SpaceLarge
import com.fnxl.bitflix.core.util.Constants
import com.fnxl.bitflix.core.util.UiEvent
import com.fnxl.bitflix.feature_auth.presentation.util.AuthError
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Destination
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val snackBar = LocalSnackbar.current
    val firstName = viewModel.firstName
    val lastName = viewModel.lastName
    val email = viewModel.email
    val password = viewModel.password
    val confirmPassword = viewModel.confirmPassword
    val inviteKey = viewModel.inviteKey
    val username = viewModel.username
    val focusManager = LocalFocusManager.current


    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.SnackBar -> {
                    snackBar.showSnackbar(event.text)
                }
                is UiEvent.Navigate -> {
                    navigator.popBackStack()
                    navigator.navigate(event.route)
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
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Join Bitflix",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold,
            )
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = "Start the experience of your life",
                style = MaterialTheme.typography.subtitle2,
            )

            SpacerMedium()
            Row(Modifier.fillMaxWidth()) {
                StandardTextField(
                    modifier = Modifier.weight(0.5f),
                    text = firstName.text,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvent.OnFirstNameChange(it))
                    },
                    error = when (firstName.error) {
                        is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                        is AuthError.InputTooShort -> stringResource(
                            id = R.string.error_input_short,
                            Constants.MIN_FIRSTNAME_LENGTH
                        )
                        else -> ""
                    },
                    placeholder = stringResource(id = R.string.firstName),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                )
                SpacerSmall()
                StandardTextField(
                    modifier = Modifier.weight(0.5f),
                    text = lastName.text,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvent.OnLastNameChange(it))
                    },
                    error = when (lastName.error) {
                        is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                        is AuthError.InputTooShort -> stringResource(
                            id = R.string.error_input_short,
                            Constants.MIN_LASTNAME_LENGTH
                        )
                        else -> ""
                    },
                    placeholder = stringResource(id = R.string.lastName),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                )
            }

            SpacerMedium()

            StandardTextField(
                text = email.text,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.OnEmailChange(it))
                },
                error = when (email.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    is AuthError.InvalidEmail -> stringResource(id = R.string.invalid_email)
                    else -> ""
                },
                placeholder = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                }),
            )
            SpacerMedium()

            StandardTextField(
                text = username.text,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.OnUsernameChange(it))
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
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                }),
            )

            SpacerMedium()

            StandardTextField(
                text = password.text,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.OnPasswordChange(it))
                },
                error = when (password.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    is AuthError.PasswordsDoNotMatch -> stringResource(id = R.string.passwords_donot_match)
                    is AuthError.InputTooShort -> stringResource(
                        id = R.string.error_input_short,
                        Constants.MIN_PASSWORD_LENGTH
                    )
                    else -> ""
                },
                placeholder = stringResource(id = R.string.password),
                keyboardType = KeyboardType.Password,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                }),
            )

            SpacerMedium()

            StandardTextField(
                text = confirmPassword.text,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.OnConfirmPasswordChange(it))
                },
                error = when (password.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    is AuthError.PasswordsDoNotMatch -> stringResource(id = R.string.passwords_donot_match)
                    is AuthError.InputTooShort -> stringResource(
                        id = R.string.error_input_short,
                        Constants.MIN_PASSWORD_LENGTH
                    )
                    else -> ""
                },
                placeholder = stringResource(id = R.string.confirmPassword),
                keyboardType = KeyboardType.Password,
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(
                        FocusDirection.Down
                    )
                }),
            )

            SpacerMedium()
            StandardTextField(
                text = inviteKey.text,
                onValueChange = {
                    viewModel.onEvent(SignUpEvent.OnInviteKeyChange(it))
                },
                error = when (password.error) {
                    is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                    is AuthError.PasswordsDoNotMatch -> stringResource(id = R.string.passwords_donot_match)
                    is AuthError.InvalidInviteKey -> stringResource(id = R.string.invalid_invitekey)
                    is AuthError.InputTooShort -> stringResource(
                        id = R.string.error_input_short,
                        Constants.MIN_USERNAME_LENGTH
                    )
                    else -> ""
                },
                placeholder = stringResource(id = R.string.invitekey),
                imeAction = ImeAction.Go,
                keyboardActions = KeyboardActions(onGo = {
                    viewModel.onEvent(SignUpEvent.OnRegister)
                    focusManager.clearFocus()
                }),
            )

            SpacerMedium()


            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {

                StandardTextButton(
                    text = "Already have an account?",
                    onClick = {
                        viewModel.onEvent(SignUpEvent.OnLoginClick)
                    },
                    style = MaterialTheme.typography.body1
                )

                StandardButton(
                    text = stringResource(id = R.string.signup),
                    onClick = { viewModel.onEvent(SignUpEvent.OnRegister) },
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp),
                )
            }
        }
    }
}
package com.willor.sentinel_v2.presentation.login


sealed interface LoginUiEvents {
    // Login
    data class LoginEmailTextChanged(val text: String): LoginUiEvents
    data class LoginPasswordTextChanged(val text: String): LoginUiEvents
    object LoginViewPasswordClicked: LoginUiEvents
    object LoginSubmitClicked: LoginUiEvents
    object GoToRegisterClicked: LoginUiEvents

    // Register
    data class RegisterEmailTextChanged(val text: String): LoginUiEvents
    data class RegisterPasswordTextChanged(val text: String): LoginUiEvents
    data class RegisterPasswordConfirmationTextChanged(val text: String): LoginUiEvents
    data class RegisterSubmitClicked(val text: String): LoginUiEvents
    object RegisterViewPasswordClicked: LoginUiEvents
    object RegisterViewConfirmPasswordClicked: LoginUiEvents
    object GoToLoginClicked: LoginUiEvents
}
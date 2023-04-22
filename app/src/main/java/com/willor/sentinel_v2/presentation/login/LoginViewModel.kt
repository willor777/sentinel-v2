package com.willor.sentinel_v2.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.willor.lib_data.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val usecases: UseCases
) : ViewModel() {

    private val tag = LoginViewModel::class.java.simpleName
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState get() = _uiState.asStateFlow()

    fun handleUiEvent(event: LoginUiEvents) {
        when (event) {
            is LoginUiEvents.LoginEmailTextChanged -> {
                updateLoginEmailText(event.text)
            }
            is LoginUiEvents.LoginPasswordTextChanged -> {
                updateLoginPasswordText(event.text)
            }
            is LoginUiEvents.LoginViewPasswordClicked -> {
                updateLoginViewPassword()
            }
            is LoginUiEvents.LoginSubmitClicked -> {
                loginUser()
            }
            is LoginUiEvents.GoToRegisterClicked -> {
                updateShowRegister()
            }
            is LoginUiEvents.RegisterEmailTextChanged -> {
                updateRegisterEmailText(event.text)
            }
            is LoginUiEvents.RegisterPasswordTextChanged -> {
                updateRegisterPasswordText(event.text)
            }
            is LoginUiEvents.RegisterPasswordConfirmationTextChanged -> {
                updateRegisterConfirmPasswordText(event.text)
            }
            is LoginUiEvents.RegisterViewPasswordClicked -> {
                updateRegisterViewPassword()
            }
            is LoginUiEvents.RegisterViewConfirmPasswordClicked -> {
                updateRegisterViewConfirmPassword()
            }
            is LoginUiEvents.RegisterSubmitClicked -> TODO()
            is LoginUiEvents.GoToLoginClicked -> {
                updateShowLogin()
            }

        }
    }

    private fun loginUser(){

    }

    private fun registerUser(){
        // Check that fields
    }

    private fun updateRegisterViewPassword() {
        _uiState.update {
            it.copy(registerViewPassword = !it.registerViewPassword)
        }
    }

    private fun updateRegisterViewConfirmPassword() {
        _uiState.update {
            it.copy(registerViewConfirmPassword = !it.registerViewConfirmPassword)
        }
    }

    private fun updateRegisterEmailText(txt: String) {
        _uiState.update {
            it.copy(registerEmailText = txt)
        }
    }

    private fun updateRegisterPasswordText(txt: String) {
        var dispText: String = ""
        var actualText = _uiState.value.registerPasswordTextActual

        // If user added a character, add that character only to ActualText
        if (txt.length > actualText.length) {
            actualText += txt[txt.lastIndex]

            // Build display text with leading *'s and last letter visible
            dispText = ""
            actualText.forEachIndexed { index, c ->
                if (index == actualText.lastIndex) {
                    dispText += c
                } else {
                    dispText += "*"
                }
            }
        }
        // If user deleted a character
        else if (txt.length < actualText.length) {
            actualText = actualText.dropLast(1)
            dispText = ""
            // Build display text
            actualText.forEachIndexed { index, c ->
                if (index == actualText.lastIndex) {
                    dispText += c
                } else {
                    dispText += "*"
                }
            }
        }

        _uiState.update {
            it.copy(
                registerPasswordTextActual = actualText,
                registerPasswordTextDisplayed = dispText
            )
        }
    }

    private fun updateRegisterConfirmPasswordText(txt: String) {
        var dispText: String = ""
        var actualText = _uiState.value.registerConfirmPasswordTextActual

        // If user added a character, add that character only to ActualText
        if (txt.length > actualText.length) {
            actualText += txt[txt.lastIndex]

            // Build display text with leading *'s and last letter visible
            dispText = ""
            actualText.forEachIndexed { index, c ->
                if (index == actualText.lastIndex) {
                    dispText += c
                } else {
                    dispText += "*"
                }
            }
        }
        // If user deleted a character
        else if (txt.length < actualText.length) {
            actualText = actualText.dropLast(1)
            dispText = ""
            // Build display text
            actualText.forEachIndexed { index, c ->
                if (index == actualText.lastIndex) {
                    dispText += c
                } else {
                    dispText += "*"
                }
            }
        }

        _uiState.update {
            it.copy(
                registerConfirmPasswordTextActual = actualText,
                registerConfirmPasswordTextDisplayed = dispText,
            )
        }
    }

    /**
     * Update Login Form Email Text
     */
    private fun updateLoginEmailText(txt: String) {
        _uiState.update {
            it.copy(loginEmailTextEditText = txt)
        }
    }

    /**
     * Update Login Form Password Text
     */
    private fun updateLoginPasswordText(txt: String) {
        var dispText: String = ""
        var actualText = _uiState.value.loginPasswordTextActual

        // If user added a character, add that character only to ActualText
        if (txt.length > actualText.length) {
            actualText += txt[txt.lastIndex]

            // Build display text with leading *'s and last letter visible
            dispText = ""
            actualText.forEachIndexed { index, c ->
                if (index == actualText.lastIndex) {
                    dispText += c
                } else {
                    dispText += "*"
                }
            }
        }
        // If user deleted a character
        else if (txt.length < actualText.length) {
            actualText = actualText.dropLast(1)
            dispText = ""
            // Build display text
            actualText.forEachIndexed { index, c ->
                if (index == actualText.lastIndex) {
                    dispText += c
                } else {
                    dispText += "*"
                }
            }
        }

        _uiState.update {
            it.copy(loginPasswordTextActual = actualText, loginPasswordTextDisplayed = dispText)
        }
    }

    /**
     * In Login Screen, Show Password instead of *'s
     */
    private fun updateLoginViewPassword() {
        _uiState.update {
            it.copy(loginViewPassword = !it.loginViewPassword)
        }
    }

    /**
     * Show Registration Form
     */
    private fun updateShowRegister() {
        Log.d(tag, "Register Clicked")
        _uiState.update {
            it.copy(isRegistering = true)
        }
    }

    /**
     * Show Login Form
     */
    private fun updateShowLogin() {
        _uiState.update {
            it.copy(isRegistering = false)
        }
    }
}


























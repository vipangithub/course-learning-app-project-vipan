package com.example.courselearningapp.presentation.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(
        LoginUiState.Idle
    )

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {
        if (email.isBlank()) {
            _uiState.value = LoginUiState.Error(
                "Email is required"
            )
            return
        }

        if (!Patterns.EMAIL_ADDRESS
                .matcher(email)
                .matches()
        ) {
            _uiState.value = LoginUiState.Error(
                "Enter a valid email address"
            )
            return
        }

        if (password.isBlank()) {
            _uiState.value = LoginUiState.Error(
                "Password is required"
            )
            return
        }

        if (password.length < 6) {
            _uiState.value = LoginUiState.Error(
                "Password must be at least 6 characters"
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            // Mock API call
            delay(1500)

            _uiState.value = LoginUiState.Success
        }
    }
}
package com.ccc.remind.presentation.ui.splash

import com.ccc.remind.domain.entity.user.User

data class SplashUiState(
    val loginState: LoginState = LoginState.EMPTY,
    val user: User? = null,
    val isInitialized: Boolean = false
)

enum class LoginState {
    EMPTY,
    LOGGED_IN_NO_DISPLAY_NAME,
    LOGGED_IN
}
package com.ccc.remind.presentation.ui.splash

import com.ccc.remind.domain.entity.user.CurrentUser

data class SplashUiState(
    val loginState: LoginState = LoginState.EMPTY,
    val currentUser: CurrentUser? = null,
    val doneUserInit: Boolean = false,
    val successFCMTokenInit: Boolean? = null
) {
    val isInitialized: Boolean
        get() = doneUserInit && successFCMTokenInit != null
}

enum class LoginState {
    EMPTY,
    LOGGED_IN_NO_DISPLAY_NAME,
    LOGGED_IN,
    REFRESH_TOKEN_EXPIRED
}
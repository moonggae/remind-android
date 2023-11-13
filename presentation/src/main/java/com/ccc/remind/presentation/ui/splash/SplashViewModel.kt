package com.ccc.remind.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.entity.user.CurrentUser
import com.ccc.remind.domain.usecase.user.GetLoggedInUserUserCase
import com.ccc.remind.domain.usecase.user.UpdateFCMTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoggedInUser: GetLoggedInUserUserCase,
    private val updateFCMToken: UpdateFCMTokenUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState

    init {
        initLoginUser()
    }

    suspend fun refreshUserFCMToken(token: String) {
        viewModelScope.launch {
            kotlin.runCatching {
                updateFCMToken(token)
            }.fold(
                onSuccess = {
                    _uiState.update { it.copy(successFCMTokenInit = true) }
                },
                onFailure = {
                    _uiState.update {
                        it.copy(
                            currentUser = null,
                            successFCMTokenInit = false,
                            loginState = LoginState.REFRESH_TOKEN_EXPIRED
                        )
                    }
                }
            )
        }
    }

    private fun initLoginUser() {
        viewModelScope.launch {
            val result = runCatching {
                getLoggedInUser().first()
            }

            val user = result.getOrNull()
            val loginState = determineLoginState(result, user)
            val successFCMTokenInit = determineSuccessFCMTokenInit(loginState)

            _uiState.update {
                it.copy(
                    currentUser = user,
                    loginState = loginState,
                    successFCMTokenInit = successFCMTokenInit,
                    doneUserInit = true
                )
            }
        }
    }

    private fun determineLoginState(result: Result<CurrentUser?>, currentUser: CurrentUser?): LoginState =
        when {
            result.isFailure -> LoginState.REFRESH_TOKEN_EXPIRED
            currentUser == null -> LoginState.EMPTY
            currentUser.displayName == null -> LoginState.LOGGED_IN_NO_DISPLAY_NAME
            else -> LoginState.LOGGED_IN
        }

    private fun determineSuccessFCMTokenInit(loginState: LoginState): Boolean? =
        when (loginState) {
            LoginState.REFRESH_TOKEN_EXPIRED, LoginState.EMPTY -> false
            else -> null
        }
}
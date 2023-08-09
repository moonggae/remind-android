package com.ccc.remind.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.GetLoggedInUserUserCase
import com.ccc.remind.presentation.di.NetworkModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoggedInUserUserCase: GetLoggedInUserUserCase
) : ViewModel() {
    companion object {
        private const val TAG = "SplashViewModel"
    }

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState

    init {
        observeAccessToken()
        initLoginUser()
    }

    private fun initLoginUser() {
        viewModelScope.launch {
            getLoggedInUserUserCase().collect { user ->
                _uiState.update {
                    it.copy(
                        user = user,
                        loginState = when {
                            user == null -> LoginState.EMPTY
                            user.displayName == null -> LoginState.LOGGED_IN_NO_DISPLAY_NAME
                            else -> LoginState.LOGGED_IN
                        }
                    )
                }
            }
        }
    }

    private fun observeAccessToken() {
        viewModelScope.launch {
            _uiState.collect {
                if(it.loginState == LoginState.LOGGED_IN) {
                    if(_uiState.value.user?.accessToken?.isNotEmpty() == true) {
                        val user = _uiState.value.user!!
                        NetworkModule.updateToken(user.accessToken, user.refreshToken)
                    }
                }
            }
        }
    }
}
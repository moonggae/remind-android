package com.ccc.remind.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.user.GetLoggedInUserUserCase
import com.ccc.remind.domain.usecase.user.UpdateFCMTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoggedInUser: GetLoggedInUserUserCase,
    private val updateFCMToken: UpdateFCMTokenUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "SplashViewModel"
    }

    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState

    init {
        initLoginUser()
    }

    suspend fun refreshUserFCMToken(token: String) {
        viewModelScope.launch {
            updateFCMToken(token)
            _uiState.update {
                it.copy(
                    doneFCMTokenInit = true
                )
            }
        }
    }

    private fun initLoginUser() {
        viewModelScope.launch {
            getLoggedInUser().collect { user ->
                _uiState.update {
                    it.copy(
                        user = user,
                        loginState = when {
                            user == null -> LoginState.EMPTY
                            user.displayName == null -> LoginState.LOGGED_IN_NO_DISPLAY_NAME
                            else -> LoginState.LOGGED_IN
                        },
                        doneUserInit = true
                    )
                }

                if(user == null) {
                    _uiState.update {
                        it.copy(
                            doneFCMTokenInit = true
                        )
                    }
                }
            }
        }
    }
}
package com.ccc.remind.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.friend.GetFriendUseCase
import com.ccc.remind.domain.usecase.user.GetLoggedInUserUserCase
import com.ccc.remind.domain.usecase.user.LogoutUseCase
import com.ccc.remind.presentation.di.network.InterceptorOkHttpClient
import com.ccc.remind.presentation.di.network.TokenInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getLoggedInUser: GetLoggedInUserUserCase,
    private val logoutUseCase: LogoutUseCase,
    private val getFriend: GetFriendUseCase,
    @InterceptorOkHttpClient private val okHttpClient: OkHttpClient
) : ViewModel() {
    private val _uiState = MutableStateFlow(SharedUiState())
    val uiState: StateFlow<SharedUiState> get() = _uiState

    companion object {
        private const val TAG = "SharedViewModel"
    }


    init {
        viewModelScope.launch {
            refreshUser()
            refreshFriend()
        }
    }

    suspend fun refreshUser() {
        getLoggedInUser().collect { newUser ->
            newUser?.let { _->
                _uiState.update { uiState ->
                    uiState.copy(
                        currentUser = newUser,
                        isInitialized = true
                    )
                }
            }
        }
    }

    suspend fun refreshFriend() {
        getFriend().collect { friend ->
            _uiState.update {
                it.copy(
                    friend = friend
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            getTokenInterceptor()?.removeToken()
            _uiState.update {
                it.copy(
                    currentUser = null
                )
            }
        }
    }

    private fun getTokenInterceptor(): TokenInterceptor? {
        return okHttpClient.interceptors.find { interceptor ->
            interceptor::class == TokenInterceptor::class
        } as TokenInterceptor?
    }
}
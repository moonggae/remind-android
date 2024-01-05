package com.ccc.remind.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.friend.GetFriendUseCase
import com.ccc.remind.domain.usecase.user.GetLoggedInUserUserCase
import com.ccc.remind.domain.usecase.user.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getLoggedInUser: GetLoggedInUserUserCase,
    private val logoutUseCase: LogoutUseCase,
    private val getFriend: GetFriendUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SharedUiState())
    val uiState: StateFlow<SharedUiState> get() = _uiState

    companion object {
        private const val TAG = "SharedViewModel"
    }

    init {
        initUser()
        initFriend()
    }

    fun initUser() {
        viewModelScope.launch {
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
    }

    fun initFriend() {
        viewModelScope.launch {
            getFriend(this).stateIn(this).collectLatest { friend ->
                _uiState.update {
                    it.copy(
                        friend = friend
                    )
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _uiState.update {
                it.copy(
                    currentUser = null
                )
            }
        }
    }
}
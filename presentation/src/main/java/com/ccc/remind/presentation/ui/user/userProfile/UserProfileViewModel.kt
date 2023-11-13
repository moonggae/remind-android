package com.ccc.remind.presentation.ui.user.userProfile

import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.usecase.user.GetUserUseCase
import com.ccc.remind.presentation.base.ComposeLifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getUser: GetUserUseCase
): ComposeLifecycleViewModel() {
    private val _uiState = MutableStateFlow(UserProfileUiState())
    val uiState: StateFlow<UserProfileUiState> get() = _uiState

    private val userId = MutableStateFlow("")

    init {
        observeUserId()
    }

    private fun observeUserId() {
        viewModelScope.launch {
            userId.collect { userId ->
                if(userId == _uiState.value.user?.id.toString()) return@collect
                if(userId.trim().isEmpty()) return@collect
                getUser(userId).collect { user ->
                    setUser(user)
                }
            }
        }
    }

    fun setUser(userId: String) {
        viewModelScope.launch {
            this@UserProfileViewModel.userId.emit(userId)
        }
    }

    fun setUser(user: User?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(user = user)
            }
        }
    }
}
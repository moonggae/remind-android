package com.ccc.remind.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.usecase.GetLoggedInUserUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getLoggedInUser: GetLoggedInUserUserCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SharedUiState())
    val uiState: StateFlow<SharedUiState> get() = _uiState


    init {
        initUser()
    }

    private fun initUser() {
        viewModelScope.launch {
            getLoggedInUser().collect {
                it?.let {
                    updateUser(it)
                }
            }
        }
    }

    fun updateUser(user: User) {
        _uiState.update {
            it.copy(user = user)
        }
    }
}
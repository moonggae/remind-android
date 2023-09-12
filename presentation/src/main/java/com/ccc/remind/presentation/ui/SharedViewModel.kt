package com.ccc.remind.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.user.GetLoggedInUserUserCase
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
        refreshUser()
    }

    fun refreshUser() {
        viewModelScope.launch {
            getLoggedInUser().collect { newUser ->
                Log.d("TAG", "SharedViewModel - refreshUser - newUser: ${newUser}")
                newUser?.let { _->
                    _uiState.update { uiState ->
                        uiState.copy(user = newUser)
                    }
                }
            }
        }
    }
}
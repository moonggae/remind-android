package com.ccc.remind.presentation.ui.onboard.displayName

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.UpdateUserDisplayNameUseCase
import com.ccc.remind.presentation.util.ValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DisplayNameViewModel @Inject constructor(private val updateUserDisplayNameUseCase: UpdateUserDisplayNameUseCase) :
    ViewModel() {
    private val _uiState = MutableStateFlow(DisplayNameUiState())
    val uiState: StateFlow<DisplayNameUiState> = _uiState.asStateFlow()

    private val _userDisplayName = MutableStateFlow<String?>(null)
    val userDisplayName: StateFlow<String?>
        get() = _userDisplayName


    init {
        initDisplayNameObserver()
    }

    fun registerDisplayName() {
        viewModelScope.launch {
            updateUserDisplayNameUseCase(userDisplayName.value ?: "")
        }
    }

    private fun initDisplayNameObserver() {
        viewModelScope.launch {
            _userDisplayName.collect { text ->
                _uiState.update {
                    it.copy(
                        isValid = ValidationUtil.displayNameValidate(text ?: ""),
                    )
                }
            }
        }
    }

    fun updateDisplayName(text: String) {
        viewModelScope.launch {
            _userDisplayName.value = text
            _uiState.update { it.copy(hasChanged = true) }
        }
    }
}
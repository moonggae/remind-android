package com.ccc.remind.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.setting.GetNotificationDeniedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNotificationDenied: GetNotificationDeniedUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUIState())
    val uiState: StateFlow<MainUIState> = _uiState

    fun checkNotificationPermission() {
        viewModelScope.launch {
            getNotificationDenied().collect { isDenied ->
                _uiState.update {
                    it.copy(
                        isDenyNotification = isDenied
                    )
                }
            }
        }
    }
}
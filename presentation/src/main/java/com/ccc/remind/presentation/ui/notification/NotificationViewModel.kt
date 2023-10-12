package com.ccc.remind.presentation.ui.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.data.util.Constants
import com.ccc.remind.domain.usecase.notification.GetNotificationsUseCase
import com.ccc.remind.domain.usecase.notification.UpdateNotificationReadAllUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val readAllNotifications: UpdateNotificationReadAllUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(NotificationUiState())

    val uiState: StateFlow<NotificationUiState>
        get() = _uiState

    fun getBeforeNotifications() {
        viewModelScope.launch {
            val page = _uiState.value.notifications.size / Constants.NOTIFICATION_LOAD_SIZE
            getNotificationsUseCase(page).collect { newNotifications ->
                val allNotifications = (_uiState.value.notifications + newNotifications)
                    .distinctBy { it.createdAt }
                    .sortedByDescending { it.createdAt }

                _uiState.update {
                    it.copy(notifications = allNotifications)
                }
            }
        }
    }

    fun initNotifications() {
        viewModelScope.launch {
            getNotificationsUseCase(0).collect { notifications ->
                _uiState.update {
                    it.copy(notifications = notifications)
                }
            }
        }
    }

    fun updateNotificationsReadAll() {
        viewModelScope.launch {
            readAllNotifications()
        }
    }
}
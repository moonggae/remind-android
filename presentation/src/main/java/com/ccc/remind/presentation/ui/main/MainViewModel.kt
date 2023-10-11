package com.ccc.remind.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ccc.remind.domain.usecase.setting.GetNotificationDeniedUseCase
import com.ccc.remind.presentation.ui.navigation.Route
import com.ccc.remind.presentation.util.notification.NotificationType
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

    fun moveToNotificationRoute(navController: NavController, notificationType: String?, notificationTargetId: String?) {
        if(notificationType == null) return

        when(NotificationType(notificationType)) {
            NotificationType.FRIEND_ACCEPT,
            NotificationType.MIND_POST -> navController.navigate(Route.Main.Home.name)

            NotificationType.MEMO_UPDATE,
            NotificationType.MEMO_COMMENT,
            NotificationType.MEMO_POST -> navController.navigate("${Route.MemoEdit.name}/-1/${notificationTargetId ?: "-1"}/true")

            NotificationType.FRIEND_REQUEST -> navController.navigate(Route.Invite.name)
            else -> {}
        }
    }
}
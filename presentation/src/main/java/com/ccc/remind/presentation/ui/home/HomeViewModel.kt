package com.ccc.remind.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ccc.remind.data.util.Constants.NOTIFICATION_LOAD_SIZE
import com.ccc.remind.domain.usecase.notification.GetNotificationsUseCase
import com.ccc.remind.domain.usecase.post.GetFriendLastPostedMindUseCase
import com.ccc.remind.domain.usecase.post.GetLastMindUseCase
import com.ccc.remind.domain.usecase.post.RequestFriendMindUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLastMind: GetLastMindUseCase,
    private val getFriendLastPostedMind: GetFriendLastPostedMindUseCase,
    private val getNotificationsUseCase: GetNotificationsUseCase,
    private val requestFriendMind: RequestFriendMindUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiStatus())

    val uiState: StateFlow<HomeUiStatus>
        get() = _uiState

//    init {
//        refreshLastPostedMind()
//        refreshFriendLastPostedMind()
//        refreshNotifications()
//    }

    fun initUiState() {
        refreshLastPostedMind()
        refreshFriendLastPostedMind()
        refreshNotifications()
    }

    private fun refreshLastPostedMind() {
        viewModelScope.launch {
            getLastMind().collect { lastMind ->
                _uiState.update {
                    it.copy(
                        post = lastMind
                    )
                }
            }
        }
    }

    private fun refreshFriendLastPostedMind() {
        viewModelScope.launch {
            getFriendLastPostedMind().collect { lastMind ->
                _uiState.update {
                    it.copy(
                        friendPost = lastMind
                    )
                }
            }
        }
    }

    private fun refreshNotifications() {
        viewModelScope.launch {
            val page = _uiState.value.notifications.size / NOTIFICATION_LOAD_SIZE
            getNotificationsUseCase(page).collect { notifications ->
                _uiState.update {
                    it.copy(
                        notifications = notifications
                    )
                }
            }
        }
    }

    fun submitRequestFriendMind() {
        // TODO: exception 처리
        viewModelScope.launch {
            requestFriendMind()
        }
    }
}
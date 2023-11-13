package com.ccc.remind.presentation.ui.friend

import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.friend.AcceptFriendRequestUseCase
import com.ccc.remind.domain.usecase.friend.CancelFriendRequestUseCase
import com.ccc.remind.domain.usecase.friend.DenyFriendRequestUseCase
import com.ccc.remind.domain.usecase.friend.GetFriendRequestsUseCase
import com.ccc.remind.domain.usecase.friend.GetReceivedFriendRequestsUseCase
import com.ccc.remind.presentation.base.ComposeLifecycleViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val getFriendRequests: GetFriendRequestsUseCase,
    private val getReceivedFriendRequests: GetReceivedFriendRequestsUseCase,
    private val acceptFriendRequest: AcceptFriendRequestUseCase,
    private val denyFriendRequest: DenyFriendRequestUseCase,
    private val cancelFriendRequest: CancelFriendRequestUseCase
): ComposeLifecycleViewModel() {
    private val _uiState = MutableStateFlow(FriendUiState())

    val uiState: StateFlow<FriendUiState>
        get() = _uiState


    fun initRequestList() {
//        initMockData()
        submitGetFriendRequests()
        submitGetReceivedFriendRequests()
    }

    private fun initMockData() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    friendRequests = getFriendRequests.mock,
                    receivedFriendRequest = getReceivedFriendRequests.mock
                )
            }
        }
    }

    private fun submitGetFriendRequests() {
        viewModelScope.launch {
            getFriendRequests().collect { requests ->
                _uiState.update {
                    it.copy(
                        friendRequests = requests
                    )
                }
            }
        }
    }

    private fun submitGetReceivedFriendRequests() {
        viewModelScope.launch {
            getReceivedFriendRequests().collect { requests ->
                _uiState.update {
                    it.copy(
                        receivedFriendRequest = requests
                    )
                }
            }
        }
    }

    suspend fun submitAcceptFriendRequest(requestId: Int) {
        viewModelScope.launch {
            runBlocking {
                acceptFriendRequest(requestId)
            }
        }
    }

    suspend fun submitDenyFriendRequest(requestId: Int) {
        viewModelScope.launch {
            runBlocking {
                denyFriendRequest(requestId)
            }
        }
    }

    suspend fun submitCancelFriendRequest(requestId: Int) {
        viewModelScope.launch {
            runBlocking {
                cancelFriendRequest(requestId)
            }
        }
    }

    fun removeOpenedProfile() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    openedUser = null
                )
            }
        }
    }
}
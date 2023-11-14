package com.ccc.remind.presentation.ui.friend.invite

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewModelScope
import com.ccc.remind.domain.usecase.friend.AcceptFriendRequestUseCase
import com.ccc.remind.domain.usecase.friend.CancelFriendRequestUseCase
import com.ccc.remind.domain.usecase.friend.DenyFriendRequestUseCase
import com.ccc.remind.domain.usecase.friend.GetFriendRequestsUseCase
import com.ccc.remind.domain.usecase.friend.GetReceivedFriendRequestsUseCase
import com.ccc.remind.domain.usecase.friend.PostFriendRequestUseCase
import com.ccc.remind.domain.usecase.user.GetUserUseCase
import com.ccc.remind.presentation.base.ComposeLifecycleViewModel
import com.ccc.remind.presentation.util.Constants.BASE_URL
import com.ccc.remind.presentation.util.Constants.INVITE_CODE_LENGTH
import com.ccc.remind.presentation.util.Constants.INVITE_URL_PATH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class InviteViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val postFriendRequest: PostFriendRequestUseCase,
    private val getFriendRequests: GetFriendRequestsUseCase,
    private val getReceivedFriendRequests: GetReceivedFriendRequestsUseCase,
    private val acceptFriendRequest: AcceptFriendRequestUseCase,
    private val denyFriendRequest: DenyFriendRequestUseCase,
    private val cancelFriendRequest: CancelFriendRequestUseCase
): ComposeLifecycleViewModel() {
    private val _uiState = MutableStateFlow(InviteUiState())

    val uiState: StateFlow<InviteUiState>
        get() = _uiState


    init {
        addOn(Lifecycle.Event.ON_START) {
            submitGetFriendRequests()
            submitGetReceivedFriendRequests()
        }
    }

    fun updateInputInviteCode(text: String) {
        val inviteUrlPrefix = "${BASE_URL}/${INVITE_URL_PATH}/"
        var pastedCode: Int? = null
        if(
            text.startsWith(inviteUrlPrefix) &&
            text.length >= inviteUrlPrefix.length + INVITE_CODE_LENGTH &&
            text.substring(inviteUrlPrefix.length, inviteUrlPrefix.length + INVITE_CODE_LENGTH).isDigitsOnly()
        ) {
            pastedCode = text
                .substring(inviteUrlPrefix.length, inviteUrlPrefix.length + INVITE_CODE_LENGTH)
                .toIntOrNull()
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    inputInviteCode = pastedCode?.toString() ?: text
                )
            }
        }
    }

    fun updateOpenProfileUser() {
        viewModelScope.launch {
            getUser.fromInviteCode(_uiState.value.inputInviteCode).collect { user ->
                _uiState.update {
                    it.copy(
                        openedUser = user
                    )
                }
            }
        }
    }

    suspend fun submitRequestFriend() {
        viewModelScope.launch {
            runBlocking {
                postFriendRequest(_uiState.value.inputInviteCode)
            }
        }
    }

    fun removeInviteCode() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    inputInviteCode = ""
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
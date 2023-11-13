package com.ccc.remind.presentation.ui.friend

import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest
import com.ccc.remind.domain.entity.user.User

data class FriendUiState(
    val openedUser: User? = null,
    val friendRequests: List<FriendRequest> = emptyList(),
    val receivedFriendRequest: List<ReceivedFriendRequest> = emptyList()
)
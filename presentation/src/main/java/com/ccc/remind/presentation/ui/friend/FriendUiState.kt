package com.ccc.remind.presentation.ui.friend

import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest

data class FriendUiState(
    val friendRequests: List<FriendRequest> = emptyList(),
    val receivedFriendRequest: List<ReceivedFriendRequest> = emptyList()
)
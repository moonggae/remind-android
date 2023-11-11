package com.ccc.remind.presentation.ui.friend

import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest
import com.ccc.remind.domain.entity.user.UserProfile

data class FriendUiState(
    val openedUserProfile: UserProfile? = null,
    val friendRequests: List<FriendRequest> = emptyList(),
    val receivedFriendRequest: List<ReceivedFriendRequest> = emptyList()
)
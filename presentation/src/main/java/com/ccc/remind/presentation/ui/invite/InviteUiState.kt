package com.ccc.remind.presentation.ui.invite

import androidx.core.text.isDigitsOnly
import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest
import com.ccc.remind.domain.entity.user.UserProfile
import com.ccc.remind.presentation.util.Constants

data class InviteUiState(
    val inputInviteCode: String = "",
    val openedUserProfile: UserProfile? = null,
    val friendRequests: List<FriendRequest> = emptyList(),
    val receivedFriendRequest: List<ReceivedFriendRequest> = emptyList()
) {
    val validInviteCode: Boolean
        get() = inputInviteCode.isDigitsOnly() && inputInviteCode.length == Constants.INVITE_CODE_LENGTH
}
package com.ccc.remind.data.source.remote.model.friend

import com.ccc.remind.data.source.remote.model.user.UserProfileVO

data class FriendRequestsDto(
    val id: Int,
    val receivedUser: UserProfileVO,
)
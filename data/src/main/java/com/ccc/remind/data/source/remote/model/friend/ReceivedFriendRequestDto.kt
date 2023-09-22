package com.ccc.remind.data.source.remote.model.friend

import com.ccc.remind.data.source.remote.model.user.UserProfileVO

data class ReceivedFriendRequestDto(
    val id: Int,
    val requestUser: UserProfileVO,
)
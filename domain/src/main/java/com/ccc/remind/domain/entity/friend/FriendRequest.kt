package com.ccc.remind.domain.entity.friend

import com.ccc.remind.domain.entity.user.User

data class FriendRequest(
    val id: Int,
    val receivedUser: User
)
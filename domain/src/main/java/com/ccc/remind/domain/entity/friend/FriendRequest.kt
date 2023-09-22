package com.ccc.remind.domain.entity.friend

import com.ccc.remind.domain.entity.user.UserProfile

data class FriendRequest(
    val id: Int,
    val receivedUser: UserProfile
)
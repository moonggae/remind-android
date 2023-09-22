package com.ccc.remind.domain.entity.friend

import com.ccc.remind.domain.entity.user.UserProfile

data class ReceivedFriendRequest(
    val id: Int,
    val requestUser: UserProfile
)
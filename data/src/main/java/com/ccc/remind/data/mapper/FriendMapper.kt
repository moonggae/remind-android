package com.ccc.remind.data.mapper

import com.ccc.remind.data.source.remote.model.friend.FriendRequestsDto
import com.ccc.remind.data.source.remote.model.friend.ReceivedFriendRequestDto
import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest

fun FriendRequestsDto.toDomain() = FriendRequest(
    id,
    receivedUser.toDomain()
)

fun ReceivedFriendRequestDto.toDomain() = ReceivedFriendRequest(
    id,
    requestUser.toDomain()
)
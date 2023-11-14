package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest
import com.ccc.remind.domain.entity.user.User
import kotlinx.coroutines.flow.Flow

interface FriendRepository {
    fun getFriend(): Flow<User?>

    suspend fun postFriendRequest(inviteCode: String)

    fun getReceivedFriendRequests(): Flow<List<ReceivedFriendRequest>>

    fun getFriendRequests(): Flow<List<FriendRequest>>

    suspend fun cancelFriendRequest(requestId: Int)

    suspend fun acceptFriendRequest(requestId: Int)

    suspend fun denyFriendRequest(requestId: Int)

    suspend fun deleteFriend()
}
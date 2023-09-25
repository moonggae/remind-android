package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.FriendRemoteService
import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest
import com.ccc.remind.domain.entity.user.UserProfile
import com.ccc.remind.domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FriendRepositoryImpl(
    private val friendRemoteService: FriendRemoteService
): FriendRepository {
    override fun getFriend(): Flow<UserProfile?> = flow {
        emit(friendRemoteService.fetchFriend().body()?.toDomain())
    }

    override fun getProfile(inviteCode: String): Flow<UserProfile> = flow {
        emit(friendRemoteService.fetchUserProfile(inviteCode).body()!!.toDomain())
    }

    override suspend fun postFriendRequest(inviteCode: String) =
        friendRemoteService.postFriendRequest(inviteCode)

    override fun getReceivedFriendRequests(): Flow<List<ReceivedFriendRequest>> = flow {
        emit(friendRemoteService.fetchReceivedFriendRequests().body()!!.map { it.toDomain() })
    }

    override fun getFriendRequests(): Flow<List<FriendRequest>> = flow {
        emit(friendRemoteService.fetchFriendRequests().body()!!.map { it.toDomain() })
    }

    override suspend fun cancelFriendRequest(requestId: Int) =
        friendRemoteService.deleteFriendRequest(requestId)

    override suspend fun acceptFriendRequest(requestId: Int) =
        friendRemoteService.postAcceptFriendRequest(requestId)

    override suspend fun denyFriendRequest(requestId: Int) =
        friendRemoteService.postDenyFriendRequest(requestId)
}
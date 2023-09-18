package com.ccc.remind.data.repository

import com.ccc.remind.data.source.remote.FriendRemoteService
import com.ccc.remind.domain.entity.user.UserProfile
import com.ccc.remind.domain.repository.FriendRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FriendRepositoryImpl(
    private val friendRemoteService: FriendRemoteService
): FriendRepository {
    override fun getProfile(inviteCode: String): Flow<UserProfile> = flow {
        emit(friendRemoteService.fetchUserProfile(inviteCode).body()!!)
    }
}
package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.user.UserProfile
import kotlinx.coroutines.flow.Flow

interface FriendRepository {
    fun getProfile(inviteCode: String): Flow<UserProfile>
}
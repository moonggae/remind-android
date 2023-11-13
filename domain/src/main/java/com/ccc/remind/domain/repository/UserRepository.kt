package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.user.User
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface UserRepository {
    fun getUser(id: UUID): Flow<User?>
    fun getUserFromInviteCode(inviteCode: String): Flow<User?>
}
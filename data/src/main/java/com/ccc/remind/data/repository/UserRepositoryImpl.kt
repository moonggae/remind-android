package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.UserRemoteService
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val userRemoteService: UserRemoteService
): UserRepository {
    override fun getUser(id: UUID): Flow<User?> = flow {
        emit(userRemoteService.fetchUser(id.toString()).body()?.toDomain())
    }

    override fun getUserFromInviteCode(inviteCode: String): Flow<User?> = flow {
        emit(userRemoteService.fetchUserByInviteCode(inviteCode).body()?.toDomain())
    }
}
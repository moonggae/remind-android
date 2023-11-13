package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: UUID): Flow<User?> = userRepository.getUser(id)
    operator fun invoke(id: String): Flow<User?> = flow {
        try {
            val uuid = UUID.fromString(id)
            invoke(uuid).collect { emit(it) }
        } catch (e: IllegalArgumentException) {
            emit(null)
        }
    }
    fun fromInviteCode(code: String): Flow<User?> = userRepository.getUserFromInviteCode(code)
}
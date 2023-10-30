package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLoggedInUserUserCase @Inject constructor(private val userRepository: UserRepository) {
    private var loggedInUser: User? = null
    suspend operator fun invoke() : Flow<User?> = flow {
        userRepository.getLoggedInUser().collect { localUser ->
            loggedInUser = localUser
        }

        loggedInUser?.let { user ->
            userRepository.getUserProfile().collect { profile ->
                loggedInUser = user.copy(
                    displayName = profile.displayName,
                    inviteCode = profile.inviteCode,
                    profileImage = profile.profileImage
                )
            }
        }

        loggedInUser?.let { user ->
            userRepository.replaceLoggedInUser(user)
        }

        emit(loggedInUser)
    }
}
package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.entity.user.LogInType
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.AuthRepository
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(accessToken: String, logInType: LogInType): Flow<User> = flow {
        lateinit var user: User

        authRepository.login(accessToken, logInType).collect { jwtToken ->
            user = User(
                accessToken = jwtToken.accessToken,
                refreshToken = jwtToken.refreshToken,
                displayName = null,
                logInType = logInType,
                profileImage = null,
                inviteCode = ""
            )
        }

        userRepository.replaceLoggedInUser(user)

        userRepository.getUserProfile().collect { profile ->
            user = user.copy(
                displayName = profile.displayName,
                profileImage = profile.profileImage,
                inviteCode = profile.inviteCode
            )
        }

        userRepository.replaceLoggedInUser(user)

        emit(user)
    }
}
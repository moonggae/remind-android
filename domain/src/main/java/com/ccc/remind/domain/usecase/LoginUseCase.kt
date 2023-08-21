package com.ccc.remind.domain.usecase

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
    suspend operator fun invoke(accessToken: String, logInType: LogInType) : Flow<User> = flow {
        lateinit var user : User

        authRepository.login(accessToken, logInType).collect { jwtToken ->
            user = User(accessToken = jwtToken.accessToken, refreshToken = jwtToken.refreshToken, displayName = null, logInType = logInType)
        }

        userRepository.getUserDisplayName().collect { displayName ->
            user = user.copy(displayName = displayName)
        }

        userRepository.replaceLoggedInUser(user)

        emit(user)
    }
}
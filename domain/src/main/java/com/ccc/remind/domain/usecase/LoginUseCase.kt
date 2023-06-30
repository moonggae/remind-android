package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.entity.LogInType
import com.ccc.remind.domain.entity.LoggedInUser
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(accessToken: String, logInType: LogInType) : Flow<LoggedInUser> = flow {
        lateinit var loggedInUser : LoggedInUser

        userRepository.login(accessToken, logInType).collect { jwtToken ->
            loggedInUser = LoggedInUser(accessToken = jwtToken.accessToken, refreshToken = jwtToken.refreshToken, displayName = null, logInType = logInType)
        }

        userRepository.getUserDisplayName().collect { displayName ->
            loggedInUser = loggedInUser.copy(displayName = displayName)
        }

        userRepository.replaceLoggedInUser(loggedInUser)

        emit(loggedInUser)
    }
}
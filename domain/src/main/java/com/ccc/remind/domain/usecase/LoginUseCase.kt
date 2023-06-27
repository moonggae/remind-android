package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.entity.JwtToken
import com.ccc.remind.domain.entity.LogInType
import com.ccc.remind.domain.entity.LoggedInUser
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(uid: String, logInType: LogInType) : Flow<JwtToken> = flow {
        userRepository.login(uid, logInType).collect {
            userRepository.updateLoggedInUser(LoggedInUser(accessToken = it.accessToken, refreshToken = it.refreshToken, displayName = null, logInType = logInType))
            emit(it)
        }
    }
}
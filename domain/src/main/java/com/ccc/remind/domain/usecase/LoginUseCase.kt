package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.entity.JwtToken
import com.ccc.remind.domain.entity.LogInType
import com.ccc.remind.domain.entity.LoggedInUser
import com.ccc.remind.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(uid: String, logInType: LogInType) : JwtToken {
        val jwtToken = userRepository.login(uid, logInType)
        userRepository.updateLoggedInUser(LoggedInUser(accessToken = jwtToken.accessToken, refreshToken = jwtToken.refreshToken, displayName = null, logInType = logInType))
        return jwtToken
    }
}
package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.entity.user.JwtToken
import com.ccc.remind.domain.repository.UserRepository
import javax.inject.Inject

class RefreshJwtTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(refreshToken: String): JwtToken {
        return userRepository.refreshJwtToken(refreshToken)
    }
}
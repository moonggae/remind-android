package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.entity.LogInType
import com.ccc.remind.domain.repository.UserRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(uid: String, logInType: LogInType) = userRepository.login(uid, logInType)
}
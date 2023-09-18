package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.repository.UserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.deleteLoggedInUser()
}
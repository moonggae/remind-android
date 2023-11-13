package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.repository.CurrentUserRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val currentUserRepository: CurrentUserRepository
) {
    suspend operator fun invoke() = currentUserRepository.deleteLoggedInUser()
}
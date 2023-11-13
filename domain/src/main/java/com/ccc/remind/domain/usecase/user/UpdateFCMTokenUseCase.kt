package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.repository.CurrentUserRepository
import javax.inject.Inject

class UpdateFCMTokenUseCase @Inject constructor(
    private val currentUserRepository: CurrentUserRepository
) {
    suspend operator fun invoke(token: String) = currentUserRepository.updateFCMToken(token)
}
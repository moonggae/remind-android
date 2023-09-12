package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserDisplayNameUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(displayName: String) = userRepository.updateUserDisplayName(displayName)
}
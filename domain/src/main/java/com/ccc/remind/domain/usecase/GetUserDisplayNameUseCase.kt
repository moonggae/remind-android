package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.repository.UserRepository
import javax.inject.Inject

class GetUserDisplayNameUseCase @Inject constructor(private val userRepository: UserRepository){
    suspend operator fun invoke() : String? = userRepository.getUserDisplayName()
}
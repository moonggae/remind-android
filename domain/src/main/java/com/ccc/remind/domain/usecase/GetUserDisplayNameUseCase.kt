package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDisplayNameUseCase @Inject constructor(private val userRepository: UserRepository){
    operator fun invoke() : Flow<String?> = userRepository.getUserDisplayName()
}
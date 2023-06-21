package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.entity.LoggedInUser
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedInUserUserCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() : LoggedInUser? {
        return userRepository.getLoggedInUser()
    }
}
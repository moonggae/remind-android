package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedInUserUserCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke() : Flow<User?> = userRepository.getLoggedInUser()

}
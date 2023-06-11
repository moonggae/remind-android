package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.entity.LoggedInUser
import com.ccc.remind.domain.repository.LoggedInUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLoggedInUserUserCase @Inject constructor(private val loggedInRepository: LoggedInUserRepository) {
    suspend operator fun invoke() : Flow<LoggedInUser?> {
        return loggedInRepository.getLoggedInUser()
    }
}
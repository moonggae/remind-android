package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.CurrentUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyProfileUseCase @Inject constructor(private val currentUserRepository: CurrentUserRepository){
    operator fun invoke() : Flow<User> = currentUserRepository.getUserProfile()
}
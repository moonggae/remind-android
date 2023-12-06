package com.ccc.remind.domain.usecase.user

import com.ccc.remind.domain.repository.CurrentUserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val currentUserRepository: CurrentUserRepository
) {
    suspend operator fun invoke() {
        currentUserRepository.getLoggedInUser().collect {
            it?.let { user ->
                // note : 순차적 실행
                runBlocking {
                    launch { currentUserRepository.deleteAccount(user.logInType) }.join()
                    launch { currentUserRepository.deleteLoggedInUser() }.join()
                }
            }
        }
    }
}
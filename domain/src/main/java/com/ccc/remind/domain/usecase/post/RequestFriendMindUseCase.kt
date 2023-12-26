package com.ccc.remind.domain.usecase.post

import com.ccc.remind.domain.repository.MindRepository
import javax.inject.Inject

class RequestFriendMindUseCase @Inject constructor(
    private val mindRepository: MindRepository
) {
    suspend operator fun invoke() = mindRepository.requestFriend()
}
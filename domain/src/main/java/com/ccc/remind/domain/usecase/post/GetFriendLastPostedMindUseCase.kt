package com.ccc.remind.domain.usecase.post

import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.repository.MindRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFriendLastPostedMindUseCase @Inject constructor(
    private val mindRepository: MindRepository
) {
    operator fun invoke(): Flow<MindPost?> = mindRepository.getFriendLast()
}
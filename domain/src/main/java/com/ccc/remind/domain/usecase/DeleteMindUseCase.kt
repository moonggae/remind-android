package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.repository.MindRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteMindUseCase @Inject constructor(
    private val mindRepository: MindRepository
) {
    suspend operator fun invoke(id: Int): Flow<Unit> = mindRepository.deleteMind(id)
}
package com.ccc.remind.domain.usecase.post

import com.ccc.remind.domain.repository.MindRepository
import javax.inject.Inject

class DeleteMindUseCase @Inject constructor(
    private val mindRepository: MindRepository
) {
    suspend operator fun invoke(id: Int) = mindRepository.delete(id)
}
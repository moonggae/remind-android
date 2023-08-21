package com.ccc.remind.domain.usecase.memo

import com.ccc.remind.domain.repository.MindMemoRepository
import javax.inject.Inject

class DeleteMemoUseCase @Inject constructor(private val mindMemoRepository: MindMemoRepository) {
    suspend operator fun invoke(id: Int) = mindMemoRepository.deleteMemo(id)
}
package com.ccc.remind.domain.usecase.memo

import com.ccc.remind.domain.repository.MindMemoRepository
import javax.inject.Inject

class UpdateMemoUseCase @Inject constructor(private val mindMemoRepository: MindMemoRepository) {
    operator fun invoke(id: Int, text: String) = mindMemoRepository.updateMemo(id, text)
}
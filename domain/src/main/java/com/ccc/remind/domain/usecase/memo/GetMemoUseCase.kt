package com.ccc.remind.domain.usecase.memo

import com.ccc.remind.domain.repository.MindMemoRepository
import javax.inject.Inject

class GetMemoUseCase @Inject constructor(private val mindMemoRepository: MindMemoRepository) {
    operator fun invoke(id: Int) = mindMemoRepository.getMemo(id)
}
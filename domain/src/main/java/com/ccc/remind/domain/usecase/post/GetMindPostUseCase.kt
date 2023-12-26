package com.ccc.remind.domain.usecase.post

import com.ccc.remind.domain.repository.MindRepository
import javax.inject.Inject

class GetMindPostUseCase @Inject constructor(
    private val mindRepository: MindRepository
) {
    operator fun invoke(id: Int) = mindRepository.get(id)
}
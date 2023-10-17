package com.ccc.remind.domain.usecase.mind

import com.ccc.remind.domain.repository.MindRepository
import javax.inject.Inject

class GetMindCardsUseCase @Inject constructor(private val mindRepository: MindRepository) {
    operator fun invoke() = mindRepository.getMindCards()
}
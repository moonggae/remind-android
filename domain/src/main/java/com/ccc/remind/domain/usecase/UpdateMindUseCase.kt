package com.ccc.remind.domain.usecase

import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.repository.MindRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class UpdateMindUseCase @Inject constructor(private val mindRepository: MindRepository) {
    operator fun invoke(
        id: Int,
        mindCards: Map<MindCard, MindCardSelectType>,
        images: List<UUID>,
        memo: String?
    ) : Flow<MindPost>
    = mindRepository.updateMinds(id, mindCards, images, memo)
}
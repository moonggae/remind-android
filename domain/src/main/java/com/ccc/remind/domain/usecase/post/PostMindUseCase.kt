package com.ccc.remind.domain.usecase.post

import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.repository.MindRepository
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class PostMindUseCase @Inject constructor(private val mindRepository: MindRepository) {
    operator fun invoke(
        mindCards: Map<MindCard, MindCardSelectType>,
        images: List<UUID>,
        memo: String?
    ) : Flow<MindPost>
    = mindRepository.post(mindCards, images, memo)
}
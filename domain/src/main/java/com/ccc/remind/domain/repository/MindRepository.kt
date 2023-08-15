package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.domain.entity.mind.MindPost
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface MindRepository {
    fun getMindCards(): Flow<List<MindCard>>

    fun postMinds(
        mindCards: Map<MindCard, MindCardSelectType>,
        images: List<UUID>,
        memo: String?
    ): Flow<MindPost>

    fun updateMinds(
        id: Int,
        mindCards: Map<MindCard, MindCardSelectType>,
        images: List<UUID>,
        memo: String?
    ): Flow<MindPost>

    suspend fun deleteMind(
        id: Int
    ): Flow<Unit>

    fun getLastPostedMind(): Flow<MindPost?>
}
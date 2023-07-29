package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.MindCard
import kotlinx.coroutines.flow.Flow

interface MindRepository {
    fun getMindCards(): Flow<List<MindCard>>

    suspend fun postMinds()
}
package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.mind.MindPostList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID

interface MindRepository {
    val mindPosts: StateFlow<List<MindPost>>

    fun getMindCards(): Flow<List<MindCard>>

    fun post(
        mindCards: Map<MindCard, MindCardSelectType>,
        images: List<UUID>,
        memo: String?
    ): Flow<MindPost>

    fun update(
        id: Int,
        mindCards: Map<MindCard, MindCardSelectType>,
        images: List<UUID>,
        memo: String?
    ): Flow<MindPost>

    suspend fun delete(id: Int)

    suspend fun requestFriend()

    fun getList(page: Int): Flow<MindPostList>

    suspend fun clearCachedPosts()

    fun observeSocket(scope: CoroutineScope)
}
package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.mind.MindPostList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import java.util.UUID

interface MindRepository {
    val postsFlow: SharedFlow<List<MindPost>>

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

    fun getFriendLastPostedMind(): Flow<MindPost?>

    suspend fun requestFriendMind()

    fun getPostList(page: Int): Flow<MindPostList>

    fun getOne(id: Int): Flow<MindPost?>

    fun observeSocket(scope: CoroutineScope)
}
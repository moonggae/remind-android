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

    suspend fun delete(
        id: Int
    ): Flow<Unit>

    fun getLast(): Flow<MindPost?>

    fun getFriendLast(): Flow<MindPost?>

    suspend fun requestFriend()

    fun getList(page: Int): Flow<MindPostList>

    fun get(id: Int): Flow<MindPost?>

    suspend fun clearCachedPosts()

    fun observeSocket(scope: CoroutineScope)
}
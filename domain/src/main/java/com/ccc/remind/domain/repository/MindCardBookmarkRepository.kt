package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.MindCardBookmark
import kotlinx.coroutines.flow.Flow

interface MindCardBookmarkRepository {
    fun getBookmarks(): Flow<List<MindCardBookmark>>

    suspend fun postBookmark(id: Int)

    suspend fun deleteBookmark(id: Int)
}
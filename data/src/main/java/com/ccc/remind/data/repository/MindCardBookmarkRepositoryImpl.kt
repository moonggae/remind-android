package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.MindCardBookmarkRemoteService
import com.ccc.remind.domain.entity.mind.MindCardBookmark
import com.ccc.remind.domain.repository.MindCardBookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MindCardBookmarkRepositoryImpl(
    private val mindCardBookmarkRemoteService: MindCardBookmarkRemoteService
): MindCardBookmarkRepository {
    override fun getBookmarks(): Flow<List<MindCardBookmark>> = flow {
        emit(mindCardBookmarkRemoteService.fetchBookmarks().body()?.map { it.toDomain() } ?: emptyList())
    }

    override suspend fun postBookmark(id: Int) {
        mindCardBookmarkRemoteService.postBookmark(id)
    }

    override suspend fun deleteBookmark(id: Int) {
        mindCardBookmarkRemoteService.deleteBookmark(id)
    }
}
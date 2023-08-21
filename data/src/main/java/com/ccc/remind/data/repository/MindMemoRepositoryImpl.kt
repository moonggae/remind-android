package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.MindMemoRemoteService
import com.ccc.remind.data.source.remote.model.mind.dto.MindCommentPostRequestDto
import com.ccc.remind.data.source.remote.model.mind.dto.MindLikeRequestDto
import com.ccc.remind.data.source.remote.model.mind.dto.MindMemoPatchRequestDto
import com.ccc.remind.data.source.remote.model.mind.dto.MindMemoPostRequestDto
import com.ccc.remind.domain.entity.mind.MindComment
import com.ccc.remind.domain.entity.mind.MindLike
import com.ccc.remind.domain.entity.mind.MindMemo
import com.ccc.remind.domain.repository.MindMemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MindMemoRepositoryImpl(private val mindMemoRemoteService: MindMemoRemoteService): MindMemoRepository {
    override fun getMemo(id: Int): Flow<MindMemo?> = flow {
        val memo = mindMemoRemoteService.fetchMemo(id)
        emit(memo.body()?.toDomain())
    }

    override fun postMemo(postId: Int, text: String): Flow<MindMemo> = flow {
        val memo = mindMemoRemoteService.postMemo(MindMemoPostRequestDto(postId, text))
        emit(memo.body()!!.toDomain())
    }

    override fun updateMemo(id: Int, text: String): Flow<MindMemo> = flow {
        val memo = mindMemoRemoteService.patchMemo(id, MindMemoPatchRequestDto(text))
        emit(memo.body()!!.toDomain())
    }

    override suspend fun deleteMemo(id: Int) {
        mindMemoRemoteService.deleteMemo(id)
    }

    override fun postComment(memoId: Int, text: String): Flow<MindComment> = flow {
        val comment = mindMemoRemoteService.postComment(MindCommentPostRequestDto(memoId, text))
        emit(comment.body()!!.toDomain())
    }

    override fun postLike(commentId: Int): Flow<MindLike> = flow {
        val like = mindMemoRemoteService.postLike(MindLikeRequestDto(commentId))
        emit(like.body()!!.toDomain())
    }

    override suspend fun deleteLike(id: Int) {
        mindMemoRemoteService.deleteLike(id)
    }

}
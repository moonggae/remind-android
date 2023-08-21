package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.MindComment
import com.ccc.remind.domain.entity.mind.MindLike
import com.ccc.remind.domain.entity.mind.MindMemo
import kotlinx.coroutines.flow.Flow

interface MindMemoRepository {
    fun getMemo(id: Int): Flow<MindMemo?>

    fun postMemo(postId:Int, text: String): Flow<MindMemo>

    fun updateMemo(id: Int, text: String): Flow<MindMemo>

    suspend fun deleteMemo(id: Int)

    fun postComment(memoId: Int, text: String): Flow<MindComment>

    fun postLike(commentId: Int): Flow<MindLike>

    suspend fun deleteLike(id: Int)
}
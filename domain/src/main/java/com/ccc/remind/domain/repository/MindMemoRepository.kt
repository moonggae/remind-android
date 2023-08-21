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
/*
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6ImU3YTNiM2ZhLWQwODctNGE2ZC1hYzg4LTFmZDEyODA0YzE1ZCIsImlhdCI6MTY5MjQzMDM3MCwiZXhwIjoxNjkyNTE2NzcwfQ._NjV0E0iLMKBW2scshy_wIixuvKAwkAKU3u2PIhKOZM
 */
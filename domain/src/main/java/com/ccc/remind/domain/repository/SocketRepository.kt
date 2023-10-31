package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.MindComment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

interface SocketRepository {
    fun watchMemoComment(memoId: Int, scope: CoroutineScope): SharedFlow<MindComment>
}
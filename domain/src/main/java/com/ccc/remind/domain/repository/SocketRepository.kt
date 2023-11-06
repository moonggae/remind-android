package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.MindComment
import com.ccc.remind.domain.entity.mind.MindPost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

interface SocketRepository {
    fun watchMemoComment(memoId: Int, scope: CoroutineScope): SharedFlow<MindComment>

    fun watchMindPost(scope: CoroutineScope): SharedFlow<MindPost>
}
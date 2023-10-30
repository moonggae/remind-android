package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.MindComment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

interface SocketRepository {
    fun watchMemoComment(scope: CoroutineScope): SharedFlow<MindComment>
}
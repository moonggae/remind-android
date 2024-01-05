package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.mind.MindComment
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

interface SocketRepository {
    fun watchMemoComment(memoId: Int, scope: CoroutineScope): SharedFlow<MindComment>

    fun watchCreateOrUpdateMindPost(scope: CoroutineScope): SharedFlow<MindPost>

    fun watchDeleteMindPost(scope: CoroutineScope): SharedFlow<Int>

    fun watchAcceptFriend(scope: CoroutineScope): SharedFlow<User>

    fun watchDeleteFriend(scope: CoroutineScope): SharedFlow<String>
}
package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.model.mind.dto.MindPostResponseDto
import com.ccc.remind.data.source.socket.SocketManager
import com.ccc.remind.data.source.socket.model.AppendMindCommentDto
import com.ccc.remind.domain.entity.mind.MindComment
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.repository.SocketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SocketRepositoryImpl(
    private val socketManager: SocketManager
): SocketRepository {
    companion object {
        private const val TAG = "SocketRepositoryImpl"
    }

    override fun watchMemoComment(memoId: Int, scope: CoroutineScope): SharedFlow<MindComment> {
        val mutableSharedFlow = MutableSharedFlow<MindComment>()
        val dataFlow: SharedFlow<AppendMindCommentDto> = socketManager.listen(
            event = "mind-memo-comment",
            classOfT =  AppendMindCommentDto::class.java, scope
        )

        scope.launch {
            dataFlow.collect { dto ->
                if(dto.memo.id == memoId) {
                    mutableSharedFlow.emit(dto.toDomain())
                }
            }
        }

        return mutableSharedFlow.asSharedFlow()
    }

    // todo : watch delete post
    override fun watchMindPost(scope: CoroutineScope): SharedFlow<MindPost> {
        val mutableSharedFlow = MutableSharedFlow<MindPost>()
        val dataFlow: SharedFlow<MindPostResponseDto> = socketManager.listen(
            event = "mind-post",
            classOfT = MindPostResponseDto::class.java,
            scope = scope
        )

        scope.launch {
            dataFlow.collect { dto ->
                mutableSharedFlow.emit(dto.toDomain())
            }
        }

        return mutableSharedFlow.asSharedFlow()
    }
}
package com.ccc.remind.data.repository

import android.util.Log
import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.model.mind.dto.MindPostResponseDto
import com.ccc.remind.data.source.remote.model.user.UserVO
import com.ccc.remind.data.source.socket.SocketManager
import com.ccc.remind.data.source.socket.model.AppendMindCommentDto
import com.ccc.remind.data.source.socket.model.DeleteMindPostDto
import com.ccc.remind.domain.entity.mind.MindComment
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.SocketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SocketRepositoryImpl(
    private val socketManager: SocketManager
) : SocketRepository {
    companion object {
        private const val TAG = "SocketRepositoryImpl"
    }

    override fun watchMemoComment(memoId: Int, scope: CoroutineScope): SharedFlow<MindComment> {
        val mutableSharedFlow = MutableSharedFlow<MindComment>()
        val dataFlow: SharedFlow<AppendMindCommentDto> = socketManager.listen(
            event = "mind-memo-comment",
            classOfT = AppendMindCommentDto::class.java, scope
        )

        scope.launch {
            dataFlow.collect { dto ->
                if (dto.memo.id == memoId) {
                    mutableSharedFlow.emit(dto.toDomain())
                }
            }
        }

        return mutableSharedFlow.asSharedFlow()
    }

    override fun watchCreateOrUpdateMindPost(scope: CoroutineScope): SharedFlow<MindPost> {
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

    override fun watchDeleteMindPost(scope: CoroutineScope): SharedFlow<Int> {
        val mutableSharedFlow = MutableSharedFlow<Int>()
        val dataFlow: SharedFlow<DeleteMindPostDto> = socketManager.listen(
            event = "mind-post-delete",
            classOfT = DeleteMindPostDto::class.java,
            scope = scope
        )

        scope.launch {
            dataFlow.collect { dto ->
                mutableSharedFlow.emit(dto.id)
            }
        }

        return mutableSharedFlow.asSharedFlow()
    }

    override fun watchAcceptFriend(scope: CoroutineScope): Flow<User> {
        val mutableSharedFlow = MutableSharedFlow<User>()
        val dataFlow = socketManager.listen<UserVO>(
            event = "friend-accept",
            classOfT = UserVO::class.java,
            scope = scope
        )

        scope.launch {
            dataFlow.collect {
                Log.d("TAG", "SocketRepositoryImpl - watchFriend - it: ${it}")
                mutableSharedFlow.emit(it.toDomain())
            }
        }

        return mutableSharedFlow
    }

    override fun watchDeleteFriend(scope: CoroutineScope): Flow<String> =
        socketManager.listen<String>(
            event = "friend-delete",
            classOfT = String::class.java,
            scope = scope
        )
}
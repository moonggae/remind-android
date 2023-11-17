package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.FriendRemoteService
import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.FriendRepository
import com.ccc.remind.domain.repository.SocketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class FriendRepositoryImpl(
    private val friendRemoteService: FriendRemoteService,
    private val socketRepository: SocketRepository
): FriendRepository {
    private val _friendFlow = MutableStateFlow<User?>(null)
    override val friendFlow: StateFlow<User?> = _friendFlow

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getFriend()
                .take(1)
                .collect {
                    _friendFlow.emit(it)
                }
        }
    }

    private fun getFriend(): Flow<User?> = flow {
        val friend = friendRemoteService.fetchFriend().body()?.toDomain()
        emit(friend)
    }

    override suspend fun postFriendRequest(inviteCode: String) =
        friendRemoteService.postFriendRequest(inviteCode)

    override fun getReceivedFriendRequests(): Flow<List<ReceivedFriendRequest>> = flow {
        emit(friendRemoteService.fetchReceivedFriendRequests().body()!!.map { it.toDomain() })
    }

    override fun getFriendRequests(): Flow<List<FriendRequest>> = flow {
        emit(friendRemoteService.fetchFriendRequests().body()!!.map { it.toDomain() })
    }

    override suspend fun cancelFriendRequest(requestId: Int) =
        friendRemoteService.deleteFriendRequest(requestId)

    override suspend fun acceptFriendRequest(requestId: Int) {
        friendRemoteService.postAcceptFriendRequest(requestId)
        val friend = friendRemoteService.fetchFriend().body()?.toDomain()
        _friendFlow.emit(friend)
    }

    override suspend fun denyFriendRequest(requestId: Int) =
        friendRemoteService.postDenyFriendRequest(requestId)

    override suspend fun deleteFriend() {
        friendRemoteService.deleteFriend()
        _friendFlow.emit(null)
    }

    override suspend fun observeSocket(scope: CoroutineScope) {
        scope.launch {
            socketRepository.watchAcceptFriend(scope).collect {
                _friendFlow.emit(it)
            }
        }

        scope.launch {
            socketRepository.watchDeleteFriend(scope).collect { deletedFriendId ->
                if(_friendFlow.value?.id.toString() == deletedFriendId) {
                    _friendFlow.emit(null)
                }
            }
        }
    }
}
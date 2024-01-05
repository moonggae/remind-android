package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.FriendRemoteService
import com.ccc.remind.domain.entity.friend.FriendRequest
import com.ccc.remind.domain.entity.friend.ReceivedFriendRequest
import com.ccc.remind.domain.entity.user.User
import com.ccc.remind.domain.repository.FriendRepository
import com.ccc.remind.domain.repository.SocketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FriendRepositoryImpl(
    private val friendRemoteService: FriendRemoteService,
    private val socketRepository: SocketRepository
) : FriendRepository {
    private val _friendFlow = MutableStateFlow<User?>(null)
    override val friendFlow: StateFlow<User?> = _friendFlow

    override suspend fun getFriend(): Flow<User?> {
        val friend = friendRemoteService.fetchFriend().body()?.toDomain()
        _friendFlow.update { friend }
        return friendFlow
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
            launch {
                socketRepository.watchAcceptFriend(scope).shareIn(
                    scope = this,
                    started = SharingStarted.Eagerly
                ).collect {
                    _friendFlow.emit(it)
                }
            }

            launch {
                socketRepository.watchDeleteFriend(scope).shareIn(
                    scope = this,
                    started = SharingStarted.Eagerly
                ).collect { deletedFriendId ->
                    if (_friendFlow.value?.id.toString() == deletedFriendId) {
                        _friendFlow.emit(null)
                    }
                }
            }
        }
    }
}
package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.MindRemoteService
import com.ccc.remind.data.source.remote.model.mind.dto.MindPostCardRequest
import com.ccc.remind.data.source.remote.model.mind.dto.MindPostRequestDto
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.entity.mind.MindPostList
import com.ccc.remind.domain.repository.MindRepository
import com.ccc.remind.domain.repository.SocketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.UUID

class MindRepositoryImpl(
    private val mindRemoteService: MindRemoteService,
    private val socketRepository: SocketRepository
) : MindRepository {
    companion object {
        private const val TAG = "MindRepositoryImpl"
    }

    private val _postsFlow = MutableSharedFlow<List<MindPost>>(replay = 1)
    override val postsFlow: SharedFlow<List<MindPost>> get() = _postsFlow

    override fun getMindCards(): Flow<List<MindCard>> = flow {
        emit(mindRemoteService.fetchMindCards().body()?.map { it.toDomain() } ?: listOf())
    }

    override fun post(
        mindCards: Map<MindCard, MindCardSelectType>,
        images: List<UUID>,
        memo: String?
    ) = flow {
        val cards = mindCards.map {
            MindPostCardRequest(
                id = it.key.id,
                type = it.value.name
            )
        }.toList()

        val dto = MindPostRequestDto(
            cards = cards,
            images = images.map { it.toString() }.toList(),
            memo = memo
        )

        val postedMind = mindRemoteService.postMindPost(dto).body()!!.toDomain()

        updateItemsInFlow(listOf(postedMind))
        emit(postedMind)
    }

    override fun update(
        id: Int,
        mindCards: Map<MindCard, MindCardSelectType>,
        images: List<UUID>,
        memo: String?
    ): Flow<MindPost> = flow {
        val cards = mindCards.map {
            MindPostCardRequest(
                id = it.key.id,
                type = it.value.name
            )
        }.toList()

        val dto = MindPostRequestDto(
            cards = cards,
            images = images.map { it.toString() }.toList(),
            memo = memo
        )

        val updatedMind = mindRemoteService.putMindPost(id, dto).body()!!.toDomain()
        updateItemsInFlow(listOf(updatedMind))
        emit(updatedMind)
    }

    override suspend fun delete(id: Int) = flow {
        emit(mindRemoteService.deleteMindPost(id))
        deleteItemInFlow(id)
    }

    override fun getLast(): Flow<MindPost?> = flow {
        emit(mindRemoteService.fetchLastPostMind().body()?.toDomain())
    }

    override fun getFriendLast(): Flow<MindPost?> = flow {
        emit(mindRemoteService.fetchFriendLastPostMind().body()?.toDomain())
    }

    override suspend fun requestFriend() {
        mindRemoteService.postRequestFriendMind()
    }

    override fun getList(page: Int): Flow<MindPostList> = flow {
        val postList = mindRemoteService.fetchMindPostPagination(page).body()!!.toDomain()
        updateItemsInFlow(postList.data)
        emit(postList)
    }

    override fun get(id: Int): Flow<MindPost?> = flow {
        emit(mindRemoteService.fetchMindPost(id).body()?.toDomain())
    }

    override suspend fun clearCachedPosts() {
        _postsFlow.emit(emptyList())
    }

    override fun observeSocket(scope: CoroutineScope) {
        scope.launch {
            socketRepository.watchMindPost(scope).collect {
                updateItemsInFlow(listOf(it))
            }
        }

        scope.launch {
            socketRepository.watchDeleteMindPost(scope).collect { deletePostId ->
                deleteItemInFlow(deletePostId)
            }
        }
    }

    private suspend fun deleteItemInFlow(id: Int) {
        val currentPosts = _postsFlow.replayCache.lastOrNull() ?: return
        val existsItemId = currentPosts.indexOfFirst { it.id == id }
        if (existsItemId > -1) {
            val updateList = currentPosts.toMutableList()
            updateList.removeAt(existsItemId)
            _postsFlow.emit(updateList)
        }
    }

    private suspend fun updateItemsInFlow(newItems: List<MindPost>) {
        val currentPosts = _postsFlow.replayCache.lastOrNull().orEmpty()

        val updatedPosts = currentPosts.toMutableList().apply {
            newItems.forEach { newItem ->
                val index = indexOfFirst { it.id == newItem.id }
                if (index != -1) {
                    set(index, newItem) // 기존 게시물 업데이트
                } else {
                    add(newItem) // 새 게시물 추가
                }
            }
        }.sortedByDescending { it.createdAt } // 날짜 기준 내림차순 정렬

        _postsFlow.emit(updatedPosts)
    }
}
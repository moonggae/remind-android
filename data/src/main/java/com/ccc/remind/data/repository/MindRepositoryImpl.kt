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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class MindRepositoryImpl(
    private val mindRemoteService: MindRemoteService,
    private val socketRepository: SocketRepository
) : MindRepository {
    companion object {
        private const val TAG = "MindRepositoryImpl"
    }

    private val _postsFlow = MutableStateFlow<List<MindPost>>(listOf())
    override val mindPosts: StateFlow<List<MindPost>> get() = _postsFlow

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

        _postsFlow.update { it.plus(postedMind) }
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
        _postsFlow.update { postList ->
            postList.filter { it.id != updatedMind.id }.plus(updatedMind)
        }
        emit(updatedMind)
    }

    override suspend fun delete(id: Int) {
        mindRemoteService.deleteMindPost(id)
        _postsFlow.update {
            it.filter { post -> post.id != id }
        }
    }

    override suspend fun requestFriend() {
        mindRemoteService.postRequestFriendMind()
    }

    override fun getList(page: Int): Flow<MindPostList> = flow {
        val postList = mindRemoteService.fetchMindPostPagination(page).body()!!.toDomain()
        _postsFlow.update { it.plus(postList.data) }
        emit(postList)
    }

    override suspend fun clearCachedPosts() {
        _postsFlow.emit(emptyList())
    }

    override fun observeSocket(scope: CoroutineScope) {
        scope.launch {
            socketRepository.watchCreateOrUpdateMindPost(scope).stateIn(scope).collectLatest { newPost ->
                val existsPost = _postsFlow.value.find { it.id == newPost.id }
                if (existsPost == null) {
                    _postsFlow.update { it.plus(newPost) }
                } else {
                    _postsFlow.update { it.filter { post -> post.id != newPost.id }.plus(newPost) }
                }
            }
        }

        scope.launch {
            socketRepository.watchDeleteMindPost(scope).stateIn(scope).collectLatest { deletePostId ->
                _postsFlow.update {
                    it.filter { post -> post.id != deletePostId }
                }
            }
        }
    }
}
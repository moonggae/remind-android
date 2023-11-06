package com.ccc.remind.data.repository

import android.util.Log
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
        emit(mindRemoteService.fetchMindCards().body()?.map { it.toDomain() } ?: listOf() )
    }

    override fun postMinds(
        mindCards: Map<MindCard, MindCardSelectType>,
        images: List<UUID>,
        memo: String?
    ) = flow {
        val cards =  mindCards.map {
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

        updatePostFlow(listOf(postedMind))
        emit(postedMind)
    }

    override fun updateMinds(
        id: Int,
        mindCards: Map<MindCard, MindCardSelectType>,
        images: List<UUID>,
        memo: String?
    ): Flow<MindPost> = flow {
        val cards =  mindCards.map {
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
        updatePostFlow(listOf(updatedMind))
        emit(updatedMind)
    }

    override suspend fun deleteMind(id: Int) = flow {
        emit(mindRemoteService.deleteMindPost(id))
        deletePostFlow(id)
    }

    override fun getLastPostedMind(): Flow<MindPost?> = flow {
        emit(mindRemoteService.fetchLastPostMind().body()?.toDomain())
    }

    override fun getFriendLastPostedMind(): Flow<MindPost?> = flow {
        emit(mindRemoteService.fetchFriendLastPostMind().body()?.toDomain())
    }

    override suspend fun requestFriendMind() {
        mindRemoteService.postRequestFriendMind()
    }

    override fun getPostList(page: Int): Flow<MindPostList> = flow {
        val postList = mindRemoteService.fetchMindPostPagination(page).body()!!.toDomain()
        updatePostFlow(postList.data)
        emit(postList)
    }

    override fun getOne(id: Int): Flow<MindPost?> = flow {
        emit(mindRemoteService.fetchMindPost(id).body()?.toDomain())
    }

    override fun observeSocket(scope: CoroutineScope) {
        scope.launch {
            socketRepository.watchMindPost(scope).collect {
                Log.d(TAG, "observeSocket - it: ${it}")
                updatePostFlow(listOf(it))
            }
        }

        scope.launch {
            socketRepository.watchDeleteMindPost(scope).collect { deletePostId ->
                deletePostFlow(deletePostId)
            }
        }
    }

    private suspend fun deletePostFlow(id: Int) {
        val currentPosts = _postsFlow.replayCache.lastOrNull() ?: return
        val existsItemId = currentPosts.indexOfFirst { it.id == id }
        if(existsItemId > -1) {
            val updateList = currentPosts.toMutableList()
            updateList.removeAt(existsItemId)
            _postsFlow.emit(updateList)
        }
    }

    private suspend fun updatePostFlow(newItems: List<MindPost>) {
        val currentPosts = _postsFlow.replayCache.lastOrNull()
        if(currentPosts == null) {
            _postsFlow.emit(newItems.sortedByDescending { it.id })
            return
        }

        val updatedPosts = currentPosts.toMutableList()
        newItems.forEach { newItem ->
            val index = updatedPosts.indexOfFirst { it.id == newItem.id }
            if (index != -1) {
                updatedPosts[index] = newItem
            } else {
                updatedPosts.add(newItem)
            }
        }

        _postsFlow.emit(updatedPosts.sortedByDescending { it.id })
    }
}
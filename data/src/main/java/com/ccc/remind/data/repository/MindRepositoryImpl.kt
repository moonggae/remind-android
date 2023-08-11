package com.ccc.remind.data.repository

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.MindRemoteService
import com.ccc.remind.data.source.remote.model.mind.dto.MindPostCardRequest
import com.ccc.remind.data.source.remote.model.mind.dto.MindPostRequestDto
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.entity.mind.MindCardSelectType
import com.ccc.remind.domain.entity.mind.MindPost
import com.ccc.remind.domain.repository.MindRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID

class MindRepositoryImpl(private val mindRemoteService: MindRemoteService) : MindRepository {
    override fun getMindCards(): Flow<List<MindCard>> = flow {
        emit(mindRemoteService.fetchMindCards().body()?.map { it.toDomain() } ?: listOf() )
    }

    override fun postMinds(mindCards: Map<MindCard, MindCardSelectType>, images: List<UUID>, memo: String?) = flow {
        val cards =  mindCards.map {
            MindPostCardRequest(
                id = it.key.id.toInt(),
                type = it.value.name
            )
        }.toList()

        val dto = MindPostRequestDto(
            cards = cards,
            images = images.map { it.toString() }.toList(),
            memo = memo
        )

        emit(mindRemoteService.postMindPost(dto).body()!!.toDomain())
    }

    override fun updateMinds(id: Int, mindCards: Map<MindCard, MindCardSelectType>, images: List<UUID>, memo: String?): Flow<MindPost> = flow {
        val cards =  mindCards.map {
            MindPostCardRequest(
                id = it.key.id.toInt(),
                type = it.value.name
            )
        }.toList()

        val dto = MindPostRequestDto(
            cards = cards,
            images = images.map { it.toString() }.toList(),
            memo = memo
        )

        emit(mindRemoteService.putMindPost(id, dto).body()!!.toDomain())
    }
}
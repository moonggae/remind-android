package com.ccc.remind.data.repositoryImp

import com.ccc.remind.data.mapper.toDomain
import com.ccc.remind.data.source.remote.MindRemoteService
import com.ccc.remind.domain.entity.mind.MindCard
import com.ccc.remind.domain.repository.MindRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MindRepositoryImpl(private val mindRemoteService: MindRemoteService) : MindRepository {
    override fun getMindCards(): Flow<List<MindCard>> = flow {
        emit(mindRemoteService.fetchMindCards().body()?.map { it.toDomain() } ?: listOf() )
    }
}
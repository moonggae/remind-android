package com.ccc.remind.data.repositoryImp

import com.ccc.remind.data.mapper.toDataModel
import com.ccc.remind.data.mapper.toDomainModel
import com.ccc.remind.data.source.local.LoggedInUserLocalDataSource
import com.ccc.remind.domain.entity.LoggedInUser
import com.ccc.remind.domain.repository.LoggedInUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoggedInUseRepositoryImpl(private val loggedInUserLocalDataSource: LoggedInUserLocalDataSource) : LoggedInUserRepository {
    override fun getLoggedInUser(): Flow<LoggedInUser?> = loggedInUserLocalDataSource.getLoggedInUser().map { it?.toDomainModel() }
}
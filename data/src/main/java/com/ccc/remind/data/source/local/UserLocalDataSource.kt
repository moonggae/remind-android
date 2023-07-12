package com.ccc.remind.data.source.local

import com.ccc.remind.data.source.local.dao.LoggedInUserDao
import com.ccc.remind.data.source.local.model.LoggedInUserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class UserLocalDataSource(
    private val loggedInUserDao: LoggedInUserDao, private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchLoggedInUser(): LoggedInUserEntity? =
        withContext(ioDispatcher) { loggedInUserDao.get() }

    suspend fun postLoggedInUser(loggedInUserEntity: LoggedInUserEntity) =
        withContext(ioDispatcher) { loggedInUserDao.insert(loggedInUserEntity) }

    suspend fun updateLoggedInUser(loggedInUserEntity: LoggedInUserEntity) =
        withContext(ioDispatcher) { loggedInUserDao.update(loggedInUserEntity) }

    suspend fun deleteLoggedInUser() =
        withContext(ioDispatcher) { loggedInUserDao.delete() }
}
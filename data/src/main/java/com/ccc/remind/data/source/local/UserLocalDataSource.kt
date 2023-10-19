package com.ccc.remind.data.source.local

import com.ccc.remind.data.source.local.dao.UserDao
import com.ccc.remind.data.source.local.model.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class UserLocalDataSource(
    private val userDao: UserDao, private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchLoggedInUser(): UserEntity? =
        withContext(ioDispatcher) { userDao.get() }

    suspend fun postLoggedInUser(userEntity: UserEntity) =
        withContext(ioDispatcher) { userDao.insert(userEntity) }

    suspend fun updateLoggedInUser(userEntity: UserEntity) =
        withContext(ioDispatcher) {
            userDao.delete()
            userDao.insert(userEntity)
        }

    suspend fun deleteLoggedInUser() =
        withContext(ioDispatcher) { userDao.delete() }
}
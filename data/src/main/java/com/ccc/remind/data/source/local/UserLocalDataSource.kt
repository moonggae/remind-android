package com.ccc.remind.data.source.local

import com.ccc.remind.data.source.local.dao.LoggedInUserDao
import com.ccc.remind.data.model.LoggedInUserEntity
import kotlinx.coroutines.flow.Flow


class UserLocalDataSource(private val loggedInUserDao: LoggedInUserDao) {
    fun getLoggedInUser(): Flow<LoggedInUserEntity?> = loggedInUserDao.get()
}
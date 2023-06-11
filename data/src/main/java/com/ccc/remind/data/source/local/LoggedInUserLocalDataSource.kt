package com.ccc.remind.data.source.local

import com.ccc.remind.data.dao.LoggedInUserDao
import com.ccc.remind.data.model.LoggedInUserEntity
import kotlinx.coroutines.flow.Flow


class LoggedInUserLocalDataSource(private val loggedInUserDao: LoggedInUserDao) {
    fun getLoggedInUser(): Flow<LoggedInUserEntity?> = loggedInUserDao.get()
}
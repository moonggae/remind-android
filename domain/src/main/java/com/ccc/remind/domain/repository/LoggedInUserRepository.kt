package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.LoggedInUser
import kotlinx.coroutines.flow.Flow

interface LoggedInUserRepository {
    fun getLoggedInUser() : Flow<LoggedInUser?>
}
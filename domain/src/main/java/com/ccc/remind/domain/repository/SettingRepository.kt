package com.ccc.remind.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getNotificationSetting(): Flow<Boolean>

    suspend fun updateNotificationSetting(enable: Boolean)

    fun getUserDenyNotificationPermission(): Flow<Boolean>

    suspend fun updateUserDenyNotificationPermission(deny: Boolean)
}
package com.ccc.remind.domain.repository

import com.ccc.remind.domain.entity.setting.HistoryViewType
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    fun getNotificationSetting(): Flow<Boolean>

    suspend fun updateNotificationSetting(enable: Boolean)

    fun getHistoryViewType(): Flow<HistoryViewType>

    suspend fun updateHistoryViewType(type: HistoryViewType)
}
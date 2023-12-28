package com.ccc.remind.data.repository

import com.ccc.remind.data.source.local.SettingLocalDataSource
import com.ccc.remind.data.source.local.model.SettingEntity
import com.ccc.remind.data.util.Constants
import com.ccc.remind.domain.entity.setting.HistoryViewType
import com.ccc.remind.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingRepositoryImpl(
    private val settingLocalDataSource: SettingLocalDataSource
) : SettingRepository {
    override fun getNotificationSetting(): Flow<Boolean> = flow {
        val settingValue = settingLocalDataSource.fetchSetting(Constants.SETTING_NOTIFICATION_KEY)?.toBooleanStrictOrNull()
        if (settingValue == null) {
            updateNotificationSetting(true)
            emit(true)
        } else {
            emit(settingValue)
        }
    }

    override suspend fun updateNotificationSetting(enable: Boolean) {
        settingLocalDataSource.updateSetting(
            SettingEntity(
                key = Constants.SETTING_NOTIFICATION_KEY,
                value = "$enable"
            )
        )
    }

    override fun getHistoryViewType(): Flow<HistoryViewType> = flow {
        val typeString = settingLocalDataSource.fetchSetting(Constants.SETTING_HISTORY_VIEW_TYPE_KEY)
        val type = try {
            HistoryViewType.valueOf(typeString ?: throw IllegalArgumentException())
        } catch (e: IllegalArgumentException) {
            updateHistoryViewType(HistoryViewType.LIST)
            HistoryViewType.LIST
        }
        emit(type)
    }

    override suspend fun updateHistoryViewType(type: HistoryViewType) {
        settingLocalDataSource.updateSetting(
            SettingEntity(
                key = Constants.SETTING_HISTORY_VIEW_TYPE_KEY,
                value = type.name
            )
        )
    }
}
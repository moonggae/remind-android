package com.ccc.remind.data.repository

import com.ccc.remind.data.source.local.SettingLocalDataSource
import com.ccc.remind.data.source.local.model.SettingEntity
import com.ccc.remind.data.util.Constants
import com.ccc.remind.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettingRepositoryImpl(
    private val settingLocalDataSource: SettingLocalDataSource
): SettingRepository {
    override fun getNotificationSetting(): Flow<Boolean> = flow {
        val settingValue = settingLocalDataSource.fetchSetting(Constants.SETTING_NOTIFICATION_KEY)
        emit(settingValue?.toBooleanStrictOrNull() == true)
    }

    override suspend fun updateNotificationSetting(enable: Boolean) {
        settingLocalDataSource.updateSetting(SettingEntity(
            key = Constants.SETTING_NOTIFICATION_KEY,
            value = "$enable"
        ))
    }

    override fun getUserDenyNotificationPermission(): Flow<Boolean> = flow {
        val settingValue = settingLocalDataSource.fetchSetting(Constants.SETTING_DENY_NOTIFICATION_KEY)
        emit(settingValue?.toBooleanStrictOrNull() == true)
    }

    override suspend fun updateUserDenyNotificationPermission(deny: Boolean) {
        settingLocalDataSource.updateSetting(SettingEntity(
            key = Constants.SETTING_NOTIFICATION_KEY,
            value = "$deny"
        ))
    }
}
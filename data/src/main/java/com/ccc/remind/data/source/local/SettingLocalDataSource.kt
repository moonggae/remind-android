package com.ccc.remind.data.source.local

import com.ccc.remind.data.source.local.dao.SettingDao
import com.ccc.remind.data.source.local.model.SettingEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


class SettingLocalDataSource(
    private val settingDao: SettingDao, private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchSetting(key: String): String? =
        withContext(ioDispatcher) { settingDao.get(key) }

    suspend fun updateSetting(settingEntity: SettingEntity) =
        withContext(ioDispatcher) { settingDao.insert(settingEntity) }

    suspend fun deleteSetting(key: String) =
        withContext(ioDispatcher) { settingDao.delete(key) }
}
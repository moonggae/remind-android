package com.ccc.remind.presentation.di

import com.ccc.remind.data.source.local.NotificationLocalDataSource
import com.ccc.remind.data.source.local.SettingLocalDataSource
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.local.dao.NotificationDao
import com.ccc.remind.data.source.local.dao.SettingDao
import com.ccc.remind.data.source.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideUserLocalDataSource(userDao: UserDao): UserLocalDataSource {
        return UserLocalDataSource(userDao, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideSettingLocalDataSource(settingDao: SettingDao): SettingLocalDataSource {
        return SettingLocalDataSource(settingDao, Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideNotificationLocalDataSource(notificationDao: NotificationDao): NotificationLocalDataSource {
        return NotificationLocalDataSource(notificationDao, Dispatchers.IO)
    }
}
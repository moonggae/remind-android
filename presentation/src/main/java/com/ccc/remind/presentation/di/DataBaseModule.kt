package com.ccc.remind.presentation.di

import android.content.Context
import androidx.room.Room
import com.ccc.remind.data.source.AppDatabase
import com.ccc.remind.data.source.local.dao.SettingDao
import com.ccc.remind.data.source.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, "app.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()

    @Provides
    @Singleton
    fun provideSettingDao(appDatabase: AppDatabase): SettingDao = appDatabase.settingDao()
}
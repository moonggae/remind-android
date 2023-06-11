package com.ccc.remind.presentation.di

import com.ccc.remind.data.dao.LoggedInUserDao
import com.ccc.remind.data.source.local.LoggedInUserLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideLoggedInUserLocalDataSource(loggedInUserDao: LoggedInUserDao) : LoggedInUserLocalDataSource {
        return LoggedInUserLocalDataSource(loggedInUserDao)
    }


}
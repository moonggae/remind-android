package com.ccc.remind.presentation.di

import com.ccc.remind.data.repositoryImp.LoggedInUseRepositoryImpl
import com.ccc.remind.data.source.local.LoggedInUserLocalDataSource
import com.ccc.remind.domain.repository.LoggedInUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoggedInUserRepository(loggedInUserLocalDataSource: LoggedInUserLocalDataSource) : LoggedInUserRepository {
        return LoggedInUseRepositoryImpl(loggedInUserLocalDataSource)
    }

}
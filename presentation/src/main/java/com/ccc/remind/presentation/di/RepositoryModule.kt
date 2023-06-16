package com.ccc.remind.presentation.di

import com.ccc.remind.data.repositoryImp.UserRepositoryImpl
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.remote.LoginService
import com.ccc.remind.domain.repository.UserRepository
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
    fun provideUserRepository(userLocalDataSource: UserLocalDataSource, loginService: LoginService) : UserRepository {
        return UserRepositoryImpl(userLocalDataSource, loginService)
    }

}
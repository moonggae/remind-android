package com.ccc.remind.presentation.di

import com.ccc.remind.data.repository.ImageRepositoryImpl
import com.ccc.remind.data.repository.MindRepositoryImpl
import com.ccc.remind.data.repository.UserRepositoryImpl
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.remote.ImageRemoteService
import com.ccc.remind.data.source.remote.LoginRemoteService
import com.ccc.remind.data.source.remote.MindRemoteService
import com.ccc.remind.domain.repository.ImageRepository
import com.ccc.remind.domain.repository.MindRepository
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
    fun provideUserRepository(userLocalDataSource: UserLocalDataSource, loginRemoteService: LoginRemoteService) : UserRepository {
        return UserRepositoryImpl(userLocalDataSource, loginRemoteService)
    }

    @Provides
    @Singleton
    fun provideMindRepository(mindRemoteService: MindRemoteService) : MindRepository {
        return MindRepositoryImpl(mindRemoteService)
    }

    @Provides
    @Singleton
    fun provideImageRepository(imageRemoteService: ImageRemoteService) : ImageRepository {
        return ImageRepositoryImpl(imageRemoteService)
    }

}
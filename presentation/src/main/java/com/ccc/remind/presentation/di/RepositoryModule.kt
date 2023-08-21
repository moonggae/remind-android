package com.ccc.remind.presentation.di

import com.ccc.remind.data.repository.AuthRepositoryImpl
import com.ccc.remind.data.repository.ImageRepositoryImpl
import com.ccc.remind.data.repository.MindMemoRepositoryImpl
import com.ccc.remind.data.repository.MindRepositoryImpl
import com.ccc.remind.data.repository.UserRepositoryImpl
import com.ccc.remind.data.source.local.UserLocalDataSource
import com.ccc.remind.data.source.remote.AuthRemoteService
import com.ccc.remind.data.source.remote.ImageRemoteService
import com.ccc.remind.data.source.remote.UserRemoteService
import com.ccc.remind.data.source.remote.MindMemoRemoteService
import com.ccc.remind.data.source.remote.MindRemoteService
import com.ccc.remind.domain.repository.AuthRepository
import com.ccc.remind.domain.repository.ImageRepository
import com.ccc.remind.domain.repository.MindMemoRepository
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
    fun provideUserRepository(userLocalDataSource: UserLocalDataSource, userRemoteService: UserRemoteService) : UserRepository {
        return UserRepositoryImpl(userLocalDataSource, userRemoteService)
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

    @Provides
    @Singleton
    fun provideMindMemoRepository(mindMemoRemoteService: MindMemoRemoteService): MindMemoRepository {
        return MindMemoRepositoryImpl(mindMemoRemoteService)
    }

    @Provides
    @Singleton
    fun providerAuthRepository(authRemoteService: AuthRemoteService, userLocalDataSource: UserLocalDataSource): AuthRepository {
        return AuthRepositoryImpl(authRemoteService, userLocalDataSource)
    }
}
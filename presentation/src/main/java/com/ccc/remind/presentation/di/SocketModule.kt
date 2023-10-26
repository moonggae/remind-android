package com.ccc.remind.presentation.di

import com.ccc.remind.data.source.remote.SocketManager
import com.ccc.remind.domain.repository.AuthRepository
import com.ccc.remind.presentation.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SocketModule {
    @Provides
    @Singleton
    fun provideSocketManager(authRepository: AuthRepository): SocketManager {
        return SocketManager().apply {
            socketConnect(
                host = Constants.BASE_URL,
                token = flow { emit(authRepository.getToken()) }
            )
        }
    }
}



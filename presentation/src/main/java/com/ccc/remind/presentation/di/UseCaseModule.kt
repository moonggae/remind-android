package com.ccc.remind.presentation.di

import com.ccc.remind.domain.repository.NotificationRepository
import com.ccc.remind.domain.usecase.notification.PostNotificationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object UseCaseModule {
//    @Provides
//    @Singleton
//    fun providePostNotificationUseCase(notificationRepository: NotificationRepository): PostNotificationUseCase {
//        return PostNotificationUseCase(notificationRepository)
//    }
//}
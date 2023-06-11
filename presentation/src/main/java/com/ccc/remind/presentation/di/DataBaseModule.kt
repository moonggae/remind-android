package com.ccc.remind.presentation.di

import android.content.Context
import androidx.room.Room
import com.ccc.remind.data.dao.LoggedInUserDao
import com.ccc.remind.data.source.AppDatabase
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
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app.db").build()

    @Provides
    @Singleton
    fun provideLoggedInUserDao(appDatabase: AppDatabase) : LoggedInUserDao = appDatabase.loggedInUserDao()
}
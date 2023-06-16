package com.ccc.remind.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ccc.remind.data.source.local.dao.LoggedInUserDao
import com.ccc.remind.data.model.LoggedInUserEntity

@Database(
    entities = [LoggedInUserEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loggedInUserDao() : LoggedInUserDao
}
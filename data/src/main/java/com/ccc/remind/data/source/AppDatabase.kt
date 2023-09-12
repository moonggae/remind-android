package com.ccc.remind.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ccc.remind.data.source.local.dao.UserDao
import com.ccc.remind.data.source.local.model.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}
package com.ccc.remind.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ccc.remind.data.source.local.dao.NotificationDao
import com.ccc.remind.data.source.local.dao.SettingDao
import com.ccc.remind.data.source.local.dao.UserDao
import com.ccc.remind.data.source.local.model.NotificationEntity
import com.ccc.remind.data.source.local.model.SettingEntity
import com.ccc.remind.data.source.local.model.UserEntity
import com.ccc.remind.data.util.LocalDataTypeConverter

@Database(
    entities = [UserEntity::class, SettingEntity::class, NotificationEntity::class],
    version = 6,
    exportSchema = true
)
@TypeConverters(LocalDataTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun settingDao(): SettingDao
    abstract fun notificationDao(): NotificationDao
}
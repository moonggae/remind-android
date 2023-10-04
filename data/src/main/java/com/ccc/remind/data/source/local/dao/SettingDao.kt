package com.ccc.remind.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ccc.remind.data.source.local.model.SettingEntity

@Dao
interface SettingDao {
    @Query("SELECT value FROM setting WHERE `key` = :key")
    fun get(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: SettingEntity)

    @Query("DELETE FROM setting WHERE `key` = :key")
    fun delete(key: String)
}
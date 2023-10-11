package com.ccc.remind.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ccc.remind.data.source.local.model.NotificationEntity

@Dao
interface NotificationDao {
    @Query("SELECT * FROM notification ORDER BY id DESC LIMIT :loadSize OFFSET :index * :loadSize")
    fun get(index: Int, loadSize: Int): List<NotificationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: NotificationEntity)

    @Update
    fun update(entities: List<NotificationEntity>)

    @Query("UPDATE notification SET isRead = 1 WHERE isRead = 0")
    fun updateReadAll()

    @Query("DELETE FROM notification")
    fun delete()
}
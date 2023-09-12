package com.ccc.remind.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ccc.remind.data.source.local.model.ImageEntity

@Dao
interface ImageDao {
    @Query("SELECT * FROM image")
    fun get(): ImageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: ImageEntity)

    @Update
    fun update(entity: ImageEntity)

    @Query("DELETE FROM image")
    fun delete()
}
package com.ccc.remind.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ccc.remind.data.source.local.model.LoggedInUserEntity

@Dao
interface LoggedInUserDao {
    @Query("SELECT * FROM logged_in_user")
    fun get(): LoggedInUserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: LoggedInUserEntity)

    @Update
    fun update(entity: LoggedInUserEntity)

    @Query("DELETE FROM logged_in_user")
    fun delete()
}
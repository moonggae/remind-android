package com.ccc.remind.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ccc.remind.data.source.local.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun get(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: UserEntity)

    @Update
    fun update(entity: UserEntity)

    @Query("DELETE FROM user")
    fun delete()
}
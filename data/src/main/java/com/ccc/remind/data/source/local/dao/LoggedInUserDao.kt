package com.ccc.remind.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ccc.remind.data.model.LoggedInUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LoggedInUserDao {
    @Query("SELECT * FROM logged_in_user")
    fun get(): Flow<LoggedInUserEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg loggedInUser: LoggedInUserEntity)

    @Delete
    fun delete(vararg  loggedInUser: LoggedInUserEntity)
}
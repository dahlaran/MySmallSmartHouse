package com.dahlaran.mysmallsmarthouse.data.database

import androidx.room.*
import com.dahlaran.mysmallsmarthouse.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers(): Int

    @Query("SELECT * FROM user_table LIMIT 1")
    suspend fun getUser(): User?
}

package com.dahlaran.mysmallsmarthouse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dahlaran.mysmallsmarthouse.models.Heater
import com.dahlaran.mysmallsmarthouse.models.Light

@Dao
interface LightDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(light: Light)

    @Query("DELETE FROM light_table WHERE id = :deviceId")
    suspend fun delete(deviceId: Int)

    @Query("DELETE FROM light_table")
    suspend fun deleteAllLights()

    @Query("SELECT * FROM light_table")
    suspend fun getLights(): List<Light>

    @Query("SELECT * FROM light_table WHERE id=:id ")
    suspend fun getLight(id: Int): Light
}
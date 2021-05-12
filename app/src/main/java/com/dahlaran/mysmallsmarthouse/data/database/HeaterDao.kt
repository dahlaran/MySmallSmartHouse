package com.dahlaran.mysmallsmarthouse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dahlaran.mysmallsmarthouse.models.Heater

@Dao
interface HeaterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(heater: Heater)

    @Query("DELETE FROM heater_table WHERE id = :deviceId")
    suspend fun delete(deviceId: Int)

    @Query("DELETE FROM heater_table")
    suspend fun deleteAllHeaters()

    @Query("SELECT * FROM heater_table")
    suspend fun getHeaters(): List<Heater>

    @Query("SELECT * FROM heater_table WHERE id=:id ")
    suspend fun getHeater(id: Int): Heater
}
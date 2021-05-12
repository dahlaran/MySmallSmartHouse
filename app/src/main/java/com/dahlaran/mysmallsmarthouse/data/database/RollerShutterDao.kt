package com.dahlaran.mysmallsmarthouse.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dahlaran.mysmallsmarthouse.models.Heater
import com.dahlaran.mysmallsmarthouse.models.RollerShutter

@Dao
interface RollerShutterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rollerShutter: RollerShutter)

    @Query("DELETE FROM roller_shutter_table WHERE id = :deviceId")
    suspend fun delete(deviceId: Int)

    @Query("DELETE FROM roller_shutter_table")
    suspend fun deleteAllRollerShutters()

    @Query("SELECT * FROM roller_shutter_table")
    suspend fun getRollerShutters(): List<RollerShutter>

    @Query("SELECT * FROM roller_shutter_table WHERE id=:id ")
    suspend fun getRollerShutter(id: Int): RollerShutter
}
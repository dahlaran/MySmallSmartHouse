package com.dahlaran.mysmallsmarthouse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dahlaran.mysmallsmarthouse.models.Heater
import com.dahlaran.mysmallsmarthouse.models.Light
import com.dahlaran.mysmallsmarthouse.models.RollerShutter

@Database(entities = [Heater::class, Light::class, RollerShutter::class], version = 1)
@TypeConverters(ModelConverter::class)
abstract class DeviceDatabase : RoomDatabase() {
    abstract fun heaterDao(): HeaterDao
    abstract fun lightDao(): LightDao
    abstract fun rollerShutterDao(): RollerShutterDao

    companion object {
        const val DB_NAME = "device.db"
    }
}
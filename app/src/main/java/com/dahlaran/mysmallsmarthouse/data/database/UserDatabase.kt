package com.dahlaran.mysmallsmarthouse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dahlaran.mysmallsmarthouse.models.User


@Database(entities = [User::class], version = 1)
@TypeConverters(ModelConverter::class)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        const val DB_NAME = "user.db"
    }
}
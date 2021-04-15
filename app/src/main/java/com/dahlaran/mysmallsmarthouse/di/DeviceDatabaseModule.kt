package com.dahlaran.mysmallsmarthouse.di

import android.content.Context
import androidx.room.Room
import com.dahlaran.mysmallsmarthouse.data.database.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeviceDatabaseModule {

    @Singleton
    @Provides
    fun provideDeviceDatabase(@ApplicationContext context: Context): DeviceDatabase {
        return Room.databaseBuilder(
            context,
            DeviceDatabase::class.java,
            DeviceDatabase.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDeviceDao(heaterDao: HeaterDao, lightDao: LightDao,rollerShutterDao: RollerShutterDao): DeviceDao {
        return DeviceDao(rollerShutterDao, heaterDao, lightDao)
    }

    @Singleton
    @Provides
    fun provideHeaterDao(deviceDatabase: DeviceDatabase): HeaterDao {
        return deviceDatabase.heaterDao()
    }

    @Singleton
    @Provides
    fun provideLightDao(deviceDatabase: DeviceDatabase): LightDao {
        return deviceDatabase.lightDao()
    }

    @Singleton
    @Provides
    fun provideRollerShutterDao(deviceDatabase: DeviceDatabase): RollerShutterDao {
        return deviceDatabase.rollerShutterDao()
    }
}
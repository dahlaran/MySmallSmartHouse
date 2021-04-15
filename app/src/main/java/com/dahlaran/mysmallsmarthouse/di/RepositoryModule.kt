package com.dahlaran.mysmallsmarthouse.di

import com.dahlaran.mysmallsmarthouse.data.DeviceRepository
import com.dahlaran.mysmallsmarthouse.data.UserRepository
import com.dahlaran.mysmallsmarthouse.data.database.DeviceDao
import com.dahlaran.mysmallsmarthouse.data.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        userDao: UserDao
    ): UserRepository {
        return UserRepository(userDao)
    }

    @Singleton
    @Provides
    fun provideDeviceRepository(
        deviceDao: DeviceDao
    ): DeviceRepository {
        return DeviceRepository(deviceDao)
    }
}

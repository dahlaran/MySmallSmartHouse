package com.dahlaran.mysmallsmarthouse.di

import com.dahlaran.mysmallsmarthouse.data.HouseRepository
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
    fun provideMainRepository(
        userDao: UserDao
    ): HouseRepository {
        return HouseRepository(userDao)
    }
}

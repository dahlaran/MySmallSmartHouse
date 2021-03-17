package com.dahlaran.mysmallsmarthouse.di

import com.dahlaran.mysmallsmarthouse.BuildConfig
import com.dahlaran.mysmallsmarthouse.data.ModuloTestApiServices
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HouseNetworkModule {

    private const val API_URL = "https://api.tvmaze.com/"

    @Provides
    fun provideGsonBuilder(): GsonBuilder {
        return GsonBuilder()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BASIC
            client.addInterceptor(interceptor)
        }

        return client.build()
    }

    @Provides
    fun provideRetrofit(gsonBuilder: GsonBuilder, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ModuloTestApiServices {
        return retrofit.create(ModuloTestApiServices::class.java)
    }
}
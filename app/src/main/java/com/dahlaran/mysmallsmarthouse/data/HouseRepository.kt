package com.dahlaran.mysmallsmarthouse.data

import com.dahlaran.mysmallsmarthouse.models.House
import com.dahlaran.mysmallsmarthouse.utils.FileUtils
import io.reactivex.Observable


object HouseRepository {

    private val houseInformation: Observable<House> by lazy {
        Observable.just(FileUtils.getHouseFromFile())
    }

    suspend fun getHouseInformation(): Observable<House> {

        return houseInformation
    }

    suspend fun updateHouseInformation(): Observable<House> {
        return houseInformation.doOnNext { Observable.just(FileUtils.getHouseFromFile()) }
    }
}
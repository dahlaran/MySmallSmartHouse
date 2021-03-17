package com.dahlaran.mysmallsmarthouse.data

import com.dahlaran.mysmallsmarthouse.models.House
import io.reactivex.Observable
import retrofit2.http.GET

interface ModuloTestApiServices {
    @GET("data.json")
    fun getHouseInformation(): Observable<House>
}
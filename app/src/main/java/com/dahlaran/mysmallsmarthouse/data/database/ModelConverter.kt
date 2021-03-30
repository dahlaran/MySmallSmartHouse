package com.dahlaran.mysmallsmarthouse.data.database

import androidx.room.TypeConverter
import com.dahlaran.mysmallsmarthouse.models.Address
import org.json.JSONObject

class ModelConverter {
    @TypeConverter
    fun fromStringToAddress(value: String): Address {
        return value.let { Address.fromJson(JSONObject(it)) }
    }

    @TypeConverter
    fun fromAddressToString(address: Address): String {
        return address.toJson()
    }
}
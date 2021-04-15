package com.dahlaran.mysmallsmarthouse.data.database

import androidx.room.TypeConverter
import com.dahlaran.mysmallsmarthouse.models.Address
import com.dahlaran.mysmallsmarthouse.models.ProductType
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

    @TypeConverter
    fun fromStringToProductType(value: String): ProductType {
        return ProductType.fromString(value)!!
    }

    @TypeConverter
    fun fromProductTypeToString(productType: ProductType): String {
        return productType.type
    }
}
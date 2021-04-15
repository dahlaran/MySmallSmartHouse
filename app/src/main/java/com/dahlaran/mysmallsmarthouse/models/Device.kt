package com.dahlaran.mysmallsmarthouse.models

import androidx.room.Entity
import androidx.room.Ignore

@Entity(tableName = "device_table")
abstract class Device(
    @Ignore open val id: Int,
    @Ignore open val name: String,
    @Ignore open val productType: ProductType
) {
    override fun equals(other: Any?): Boolean {
        return (other != null && other is Device &&
                other.id == id &&
                other.name == name &&
                other.productType == productType)
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + productType.hashCode()
        return result
    }

}

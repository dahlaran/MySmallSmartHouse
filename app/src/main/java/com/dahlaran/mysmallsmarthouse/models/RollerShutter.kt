package com.dahlaran.mysmallsmarthouse.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONException
import org.json.JSONObject

@Entity(tableName = "roller_shutter_table")
class RollerShutter(
    @PrimaryKey override val id: Int,
    override val name: String,
    val position: Int,
    override val productType: ProductType
) : Device(id, name, productType) {

    companion object {
        @Throws(JSONException::class)
        fun fromJson(json: JSONObject): RollerShutter {
            return RollerShutter(
                json.getInt("id"),
                json.getString("deviceName"),
                json.getInt("position"),
                ProductType.fromJson(json)
            )
        }
    }
}
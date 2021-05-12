package com.dahlaran.mysmallsmarthouse.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONException
import org.json.JSONObject

@Entity(tableName = "light_table")
data class Light(
    @PrimaryKey override val id: Int,
    override val name: String,
    var intensity: Int,
    var mode: String,
    override val productType: ProductType
) : Device(id, name, productType) {
    companion object {
        @Throws(JSONException::class)
        fun fromJson(json: JSONObject): Light {
            return Light(
                json.getInt("id"),
                json.getString("deviceName"),
                json.getInt("intensity"),
                json.getString("mode"),
                ProductType.fromJson(json)
            )
        }

        fun convertStatusToMode(status: Boolean): String {
            return if (status) "ON" else "OFF"
        }
    }

    fun status(): Boolean{
        return "ON" == mode
    }

    fun intensityToShow(): String {
        return intensity.toString()
    }
}
package com.dahlaran.mysmallsmarthouse.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONException
import org.json.JSONObject

@Entity(tableName = "heater_table")
data class Heater(
    @PrimaryKey override val id: Int,
    override val name: String,
    val mode: String,
    override val productType: ProductType,
    val temperature: Int
) : Device(id, name, productType) {

    companion object {
        @Throws(JSONException::class)
        fun fromJson(json: JSONObject): Heater {
            return Heater(
                json.getInt("id"),
                json.getString("deviceName"),
                json.getString("mode"),
                ProductType.fromJson(json),
                json.getInt("temperature")
            )
        }

        fun convertStatusToMode(status: Boolean): String {
            return if (status) "ON" else "OFF"
        }
    }

    fun status(): Boolean {
        return "ON" == mode
    }

    fun temperatureToShow(): String {
        return temperature.toString()
    }
}
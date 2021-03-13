package com.dahlaran.mysmallsmarthouse.models

import org.json.JSONException
import org.json.JSONObject

data class Heater(
    override val id: Int,
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
    }
}
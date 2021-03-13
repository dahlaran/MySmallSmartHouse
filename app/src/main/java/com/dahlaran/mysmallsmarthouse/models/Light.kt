package com.dahlaran.mysmallsmarthouse.models

import org.json.JSONException
import org.json.JSONObject

data class Light(
    override val id: Int,
    override val name: String,
    val intensity: Int,
    val mode: String,
    override val productType: ProductType
): Device(id, name, productType) {
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
    }
}
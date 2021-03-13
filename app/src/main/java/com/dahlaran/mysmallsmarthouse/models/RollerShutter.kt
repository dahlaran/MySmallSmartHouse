package com.dahlaran.mysmallsmarthouse.models

import org.json.JSONException
import org.json.JSONObject

class RollerShutter(
    override val id: Int,
    override val name : String,
    val position: Int,
    override val productType: ProductType
): Device(id, name, productType) {

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
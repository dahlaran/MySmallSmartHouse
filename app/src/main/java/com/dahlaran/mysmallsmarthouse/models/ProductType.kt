package com.dahlaran.mysmallsmarthouse.models

import org.json.JSONException
import org.json.JSONObject

enum class ProductType(val type: String) {
    LIGHT("Light"), ROLLER_SHUTTER("RollerShutter"), HEATER("Heater");

    companion object{
        @Throws(JSONException::class)
        fun fromJson(json: JSONObject): ProductType {

            return when (json.getString("productType")) {
                LIGHT.type -> LIGHT
                HEATER.type -> HEATER
                ROLLER_SHUTTER.type -> ROLLER_SHUTTER

                // TODO: Create a generic type if none of the above items; maybe NONE
                // TODO: v2 Right now it don't take account if type is not of one above (not urgent)
                else -> LIGHT
            }
        }

        fun fromString(string: String): ProductType? {
            return when (string) {
                LIGHT.type -> LIGHT
                HEATER.type -> HEATER
                ROLLER_SHUTTER.type -> ROLLER_SHUTTER

                else -> null
            }
        }
    }
}
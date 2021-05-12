package com.dahlaran.mysmallsmarthouse.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONException
import org.json.JSONObject

data class Address(
    val city: String,
    val country: String,
    val postalCode: Int,
    val street: String,
    val streetCode: String
) {
    companion object {
        @Throws(JSONException::class)
        fun fromJson(json: JSONObject): Address {
            return Address(
                json.getString("city"),
                json.getString("country"),
                json.getInt("postalCode"),
                json.getString("street"),
                json.getString("streetCode")
            )
        }
    }

    fun postalToShow(): String {
        return postalCode.toString()
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}

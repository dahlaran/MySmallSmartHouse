package com.dahlaran.mysmallsmarthouse.models

import org.json.JSONException
import org.json.JSONObject

data class User(
    val address: Address,
    val birthDate: Long,
    val firstName: String,
    val lastName: String
) {
    companion object {
         @Throws(JSONException::class)
        fun fromJson(json: JSONObject): User {
            return User(
                Address.fromJson(json.getJSONObject("address")),
                json.getLong("birthDate"),
                json.getString("firstName"),
                json.getString("lastName")
            )
        }
    }
}
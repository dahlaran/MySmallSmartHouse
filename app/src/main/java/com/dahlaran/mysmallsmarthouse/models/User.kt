package com.dahlaran.mysmallsmarthouse.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dahlaran.mysmallsmarthouse.utils.DateUtils
import org.json.JSONException
import org.json.JSONObject

@Entity(tableName = "user_table")
data class User(
    @ColumnInfo(name ="address") val address: Address,
    @ColumnInfo(name ="birthdate") val birthDate: Long,
    @ColumnInfo(name ="first_name") val firstName: String,
    @ColumnInfo(name ="last_name") val lastName: String,
    @PrimaryKey @ColumnInfo(name ="primary_id") val id: Int = 1
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

    fun birthDateToShow(): String {
        return DateUtils.convertLongToDateString(birthDate)
    }
}
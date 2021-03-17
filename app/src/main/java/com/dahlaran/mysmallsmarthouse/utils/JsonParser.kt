package com.dahlaran.mysmallsmarthouse.utils

import android.content.Context
import com.dahlaran.mysmallsmarthouse.models.*
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject


object JsonParser {

    fun parseJsonToHouse(jsonToParse: String): House {
        var user: User? = null
        var deviceList: List<Device> = mutableListOf()
        if (jsonToParse.isEmpty()) {
            return House(deviceList, user)
        }
        val jsonObject = JSONObject(jsonToParse)
        val iterator = jsonObject.keys()

        while (iterator.hasNext()) {
            val key: String = iterator.next()
            if (key == "devices") {
                deviceList = convertJsonObjectToDevicesList(jsonObject.getJSONArray(key))
            } else {
                val value = jsonObject.getJSONObject(key)
                user = User.fromJson(value)
            }
        }

        return House(deviceList, user)
    }

    fun convertJsonObjectToDevicesList(jsonDeviceList: JSONArray): List<Device> {
        val deviceList = mutableListOf<Device>()
        val length = jsonDeviceList.length()
        var iterator = 0

        while (iterator < length) {
            val value = jsonDeviceList.get(iterator)
            val device = convertJsonObjectToDeviceObject(value as JSONObject)
            if (device != null) {
                deviceList.add(device)
            }
            ++iterator
        }
        return deviceList
    }

    fun convertJsonObjectToDeviceObject(jsonObject: JSONObject): Device? {
        val type = jsonObject.getString("productType")
        return when (type) {
            ProductType.LIGHT.type -> Light.fromJson(jsonObject)
            ProductType.HEATER.type -> Heater.fromJson(jsonObject)
            ProductType.ROLLER_SHUTTER.type -> RollerShutter.fromJson(jsonObject)
            else -> null
        }
    }
}
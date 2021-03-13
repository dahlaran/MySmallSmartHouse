package com.dahlaran.mysmallsmarthouse.data

import com.dahlaran.mysmallsmarthouse.models.ProductType
import org.json.JSONObject
import org.junit.Test

class ProductTypeTest {
    @Test
    fun fromJsonTestHeater() {
        val data = "{\n" +
                "id: 6,\n" +
                "deviceName: \"Radiateur - Salon\",\n" +
                "mode: \"OFF\",\n" +
                "temperature: 18,\n" +
                "productType: \"Heater\"\n" +
                "}\n"
        val productType: ProductType = ProductType.fromJson(JSONObject(data))
        assert(productType == ProductType.HEATER)
    }

    @Test
    fun fromJsonTestLight() {
        val data = "{\n" +
                "id: 10,\n" +
                "deviceName: \"Lampe - Salle de bain\",\n" +
                "intensity: 36,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "}"
        val productType: ProductType = ProductType.fromJson(JSONObject(data))
        assert(productType == ProductType.LIGHT)
    }

    @Test
    fun fromJsonTestRollerShutter() {
        val data = "{\n" +
                "id: 8,\n" +
                "deviceName: \"Volet roulant - Salle de bain\",\n" +
                "position: 70,\n" +
                "productType: \"RollerShutter\"\n" +
                "}"
        val productType: ProductType = ProductType.fromJson(JSONObject(data))
        assert(productType == ProductType.ROLLER_SHUTTER)
    }
}
package com.dahlaran.mysmallsmarthouse.data

import com.dahlaran.mysmallsmarthouse.models.Heater
import com.dahlaran.mysmallsmarthouse.models.ProductType
import org.json.JSONObject
import org.junit.Test

class HeaterTest {

    @Test
    fun fromJsonTest() {
        val data = "{\n" +
                "id: 6,\n" +
                "deviceName: \"Radiateur - Salon\",\n" +
                "mode: \"OFF\",\n" +
                "temperature: 18,\n" +
                "productType: \"Heater\"\n" +
                "}\n"
        val heater: Heater = Heater.fromJson(JSONObject(data))

        assert(heater.id == 6)
        assert(heater.name == "Radiateur - Salon")
        assert(heater.mode == "OFF")
        assert(heater.temperature == 18)
        assert(heater.productType == ProductType.HEATER)
    }
}
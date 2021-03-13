package com.dahlaran.mysmallsmarthouse.data

import com.dahlaran.mysmallsmarthouse.models.Light
import com.dahlaran.mysmallsmarthouse.models.ProductType
import org.json.JSONObject
import org.junit.Test

class LightTest {

    @Test
    fun fromJsonTest() {
        val data = "{\n" +
                "id: 10,\n" +
                "deviceName: \"Lampe - Salle de bain\",\n" +
                "intensity: 36,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "}"
        val light: Light = Light.fromJson(JSONObject(data))

        assert(light.id == 10)
        assert(light.name == "Lampe - Salle de bain")
        assert(light.mode == "ON")
        assert(light.intensity == 36)
        assert(light.productType == ProductType.LIGHT)
    }
}
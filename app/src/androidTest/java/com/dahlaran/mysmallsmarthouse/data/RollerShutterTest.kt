package com.dahlaran.mysmallsmarthouse.data

import com.dahlaran.mysmallsmarthouse.models.ProductType
import com.dahlaran.mysmallsmarthouse.models.RollerShutter
import org.json.JSONObject
import org.junit.Test

class RollerShutterTest {

    @Test
    fun fromJsonTest() {
        val data = "{\n" +
                "id: 8,\n" +
                "deviceName: \"Volet roulant - Salle de bain\",\n" +
                "position: 70,\n" +
                "productType: \"RollerShutter\"\n" +
                "}"
        val roller: RollerShutter = RollerShutter.fromJson(JSONObject(data))
        assert(roller.id == 8)
        assert(roller.name == "Volet roulant - Salle de bain")
        assert(roller.position == 70)
         assert(roller.productType == ProductType.ROLLER_SHUTTER)
    }
}
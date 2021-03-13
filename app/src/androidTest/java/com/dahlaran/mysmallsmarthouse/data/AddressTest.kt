package com.dahlaran.mysmallsmarthouse.data

import com.dahlaran.mysmallsmarthouse.models.Address
import org.json.JSONObject
import org.junit.Test

class AddressTest {

    @Test
    fun fromJsonTest() {
        val data = "{\n" +
                "city: \"Issy-les-Moulineaux\",\n" +
                "postalCode: 92130,\n" +
                "street: \"rue Michelet\",\n" +
                "streetCode: \"2B\",\n" +
                "country: \"France\"\n" +
                "}"
        val address: Address = Address.fromJson(JSONObject(data))

        assert(address.city == "Issy-les-Moulineaux")
        assert(address.postalCode == 92130)
        assert(address.street == "rue Michelet")
        assert(address.streetCode == "2B")
        assert(address.country == "France")
    }
}
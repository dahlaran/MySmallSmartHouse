package com.dahlaran.mysmallsmarthouse.data

import com.dahlaran.mysmallsmarthouse.models.User
import org.json.JSONObject
import org.junit.Test

class UserTest {

    @Test
    fun fromJsonTest() {
        val data = "{\n" +
                "firstName: \"John\",\n" +
                "lastName: \"Doe\",\n" +
                "address: {\n" +
                "city: \"Issy-les-Moulineaux\",\n" +
                "postalCode: 92130,\n" +
                "street: \"rue Michelet\",\n" +
                "streetCode: \"2B\",\n" +
                "country: \"France\"\n" +
                "},\n" +
                "birthDate: 813766371000\n" +
                "}"
        val user: User = User.fromJson(JSONObject(data))
        assert(user.birthDate == 813766371000)
        assert(user.firstName == "John")
        assert(user.lastName == "Doe")
    }
}
package com.dahlaran.mysmallsmarthouse.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.dahlaran.mysmallsmarthouse.models.Heater
import com.dahlaran.mysmallsmarthouse.models.Light
import com.dahlaran.mysmallsmarthouse.models.RollerShutter
import org.json.JSONObject
import org.junit.Before
import org.junit.Test

class JsonParserTest {
    lateinit var context: Context

    @Before
    fun before() {
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun getDevicesFromFileTest() {
        val house = JsonParser.getHouseFromFile()
        assert(house.deviceList.isNotEmpty())
        assert(house.user != null)
    }

    @Test
    fun parseJsonToDevicesTestEmpty() {
        val house = JsonParser.parseJsonToHouse("")
        assert(house.deviceList.isEmpty())
        assert(house.user == null)
    }

    @Test
    fun parseJsonToDevicesTest() {
        val data = "{\n" +
                "devices: [\n" +
                "{\n" +
                "id: 1,\n" +
                "deviceName: \"Lampe - Cuisine\",\n" +
                "intensity: 50,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "},\n" +
                "{\n" +
                "id: 2,\n" +
                "deviceName: \"Volet roulant - Salon\",\n" +
                "position: 70,\n" +
                "productType: \"RollerShutter\"\n" +
                "},\n" +
                "{\n" +
                "id: 3,\n" +
                "deviceName: \"Radiateur - Chambre\",\n" +
                "mode: \"OFF\",\n" +
                "temperature: 20,\n" +
                "productType: \"Heater\"\n" +
                "},\n" +
                "{\n" +
                "id: 4,\n" +
                "deviceName: \"Lampe - Salon\",\n" +
                "intensity: 100,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "},\n" +
                "{\n" +
                "id: 5,\n" +
                "deviceName: \"Volet roulant\",\n" +
                "position: 0,\n" +
                "productType: \"RollerShutter\"\n" +
                "},\n" +
                "{\n" +
                "id: 6,\n" +
                "deviceName: \"Radiateur - Salon\",\n" +
                "mode: \"OFF\",\n" +
                "temperature: 18,\n" +
                "productType: \"Heater\"\n" +
                "},\n" +
                "{\n" +
                "id: 7,\n" +
                "deviceName: \"Lampe - Grenier\",\n" +
                "intensity: 0,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "},\n" +
                "{\n" +
                "id: 8,\n" +
                "deviceName: \"Volet roulant - Salle de bain\",\n" +
                "position: 70,\n" +
                "productType: \"RollerShutter\"\n" +
                "},\n" +
                "{\n" +
                "id: 9,\n" +
                "deviceName: \"Radiateur - Salle de bain\",\n" +
                "mode: \"OFF\",\n" +
                "temperature: 20,\n" +
                "productType: \"Heater\"\n" +
                "},\n" +
                "{\n" +
                "id: 10,\n" +
                "deviceName: \"Lampe - Salle de bain\",\n" +
                "intensity: 36,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "},\n" +
                "{\n" +
                "id: 11,\n" +
                "deviceName: \"Volet roulant\",\n" +
                "position: 33,\n" +
                "productType: \"RollerShutter\"\n" +
                "},\n" +
                "{\n" +
                "id: 12,\n" +
                "deviceName: \"Radiateur - WC\",\n" +
                "mode: \"ON\",\n" +
                "temperature: 19,\n" +
                "productType: \"Heater\"\n" +
                "}\n" +
                "],\n" +
                "user: {\n" +
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
                "}\n" +
                "}"

        val house = JsonParser.parseJsonToHouse(data)
        assert(house.deviceList.isNotEmpty())
        assert(house.user != null)

        assert(house.deviceList.size == 12)
        assert(house.user!!.firstName == "John")
        assert(house.user!!.lastName == "Doe")
    }

    @Test
    fun convertJsonObjectToDevicesListTest() {
        val data = "{devices: [\n" +
                "{\n" +
                "id: 1,\n" +
                "deviceName: \"Lampe - Cuisine\",\n" +
                "intensity: 50,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "},\n" +
                "{\n" +
                "id: 2,\n" +
                "deviceName: \"Volet roulant - Salon\",\n" +
                "position: 70,\n" +
                "productType: \"RollerShutter\"\n" +
                "},\n" +
                "{\n" +
                "id: 3,\n" +
                "deviceName: \"Radiateur - Chambre\",\n" +
                "mode: \"OFF\",\n" +
                "temperature: 20,\n" +
                "productType: \"Heater\"\n" +
                "},\n" +
                "{\n" +
                "id: 4,\n" +
                "deviceName: \"Lampe - Salon\",\n" +
                "intensity: 100,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "},\n" +
                "{\n" +
                "id: 5,\n" +
                "deviceName: \"Volet roulant\",\n" +
                "position: 0,\n" +
                "productType: \"RollerShutter\"\n" +
                "},\n" +
                "{\n" +
                "id: 6,\n" +
                "deviceName: \"Radiateur - Salon\",\n" +
                "mode: \"OFF\",\n" +
                "temperature: 18,\n" +
                "productType: \"Heater\"\n" +
                "},\n" +
                "{\n" +
                "id: 7,\n" +
                "deviceName: \"Lampe - Grenier\",\n" +
                "intensity: 0,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "},\n" +
                "{\n" +
                "id: 8,\n" +
                "deviceName: \"Volet roulant - Salle de bain\",\n" +
                "position: 70,\n" +
                "productType: \"RollerShutter\"\n" +
                "},\n" +
                "{\n" +
                "id: 9,\n" +
                "deviceName: \"Radiateur - Salle de bain\",\n" +
                "mode: \"OFF\",\n" +
                "temperature: 20,\n" +
                "productType: \"Heater\"\n" +
                "},\n" +
                "{\n" +
                "id: 10,\n" +
                "deviceName: \"Lampe - Salle de bain\",\n" +
                "intensity: 36,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "},\n" +
                "{\n" +
                "id: 11,\n" +
                "deviceName: \"Volet roulant\",\n" +
                "position: 33,\n" +
                "productType: \"RollerShutter\"\n" +
                "},\n" +
                "{\n" +
                "id: 12,\n" +
                "deviceName: \"Radiateur - WC\",\n" +
                "mode: \"ON\",\n" +
                "temperature: 19,\n" +
                "productType: \"Heater\"\n" +
                "}\n" +
                "]}"
        val json = JSONObject(data)
        val value = json.getJSONArray("devices")
        val deviceList = JsonParser.convertJsonObjectToDevicesList(value)
        assert(deviceList.isNotEmpty())

        assert(deviceList.size == 12)
    }


    @Test
    fun convertJsonObjectToDeviceObjectTestHeater() {
        val data = "{\n" +
                "id: 6,\n" +
                "deviceName: \"Radiateur - Salon\",\n" +
                "mode: \"OFF\",\n" +
                "temperature: 18,\n" +
                "productType: \"Heater\"\n" +
                "}\n"
        val device = JsonParser.convertJsonObjectToDeviceObject(JSONObject(data))
        assert(device != null)

        assert(device is Heater)
    }

    @Test
    fun convertJsonObjectToDeviceObjectTestRollerShutter() {
        val data = "{\n" +
                "id: 8,\n" +
                "deviceName: \"Volet roulant - Salle de bain\",\n" +
                "position: 70,\n" +
                "productType: \"RollerShutter\"\n" +
                "}"
        val device = JsonParser.convertJsonObjectToDeviceObject(JSONObject(data))
        assert(device != null)

        assert(device is RollerShutter)
    }

    @Test
    fun convertJsonObjectToDeviceObjectTestLight() {
        val data = "{\n" +
                "id: 1,\n" +
                "deviceName: \"Lampe - Cuisine\",\n" +
                "intensity: 50,\n" +
                "mode: \"ON\",\n" +
                "productType: \"Light\"\n" +
                "}"
        val device = JsonParser.convertJsonObjectToDeviceObject(JSONObject(data))
        assert(device != null)

        assert(device is Light)
    }

    @Test
    fun convertJsonObjectToDeviceObjectTestUnknown() {
        val data = "{\n" +
                "id: 8,\n" +
                "deviceName: \"Volet roulant - Salle de bain\",\n" +
                "position: 70,\n" +
                "productType: \"LIGhtScar\"\n" +
                "}"
        val device = JsonParser.convertJsonObjectToDeviceObject(JSONObject(data))
        assert(device == null)
    }
}
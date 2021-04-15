package com.dahlaran.mysmallsmarthouse.data.database

import com.dahlaran.mysmallsmarthouse.models.*
import kotlinx.coroutines.runBlocking

class DeviceDao(
    private val rollerShutterDao: RollerShutterDao,
    private val heaterDao: HeaterDao,
    private val lightDao: LightDao
) {

    fun getDevices(): List<Device> {
        val deviceList = mutableListOf<Device>()
        runBlocking {
            deviceList.addAll(heaterDao.getHeaters())
            deviceList.addAll(lightDao.getLights())
            deviceList.addAll(rollerShutterDao.getRollerShutters())
            deviceList.sortBy { it.id }
        }
        return deviceList
    }

    fun removeDevice(id: Int) {
        runBlocking {
            heaterDao.delete(id)
            lightDao.delete(id)
            rollerShutterDao.delete(id)
        }
    }

    fun removeAllDevices() {
        runBlocking {
            heaterDao.deleteAllHeaters()
            lightDao.deleteAllLights()
            rollerShutterDao.deleteAllRollerShutters()
        }
    }

    fun addAllDevices(deviceList: List<Device>) {
        runBlocking {
            deviceList.forEach {
                when (it.productType) {
                    ProductType.HEATER -> heaterDao.insert(it as Heater)
                    ProductType.LIGHT -> lightDao.insert(it as Light)
                    ProductType.ROLLER_SHUTTER -> rollerShutterDao.insert(it as RollerShutter)
                }
            }

        }
    }
}
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
                saveDeviceDependingOfType(it)
            }
        }
    }

    fun getDevice(deviceId: Int): Device? {
        var device: Device?
        runBlocking {
            device = heaterDao.getHeater(deviceId)
            if (device == null) {
                device = lightDao.getLight(deviceId)
                if (device == null) {
                    device = rollerShutterDao.getRollerShutter(deviceId)
                }
            }
        }
        return device
    }

    fun saveDevice(deviceToSave: Device): Device {
        return runBlocking {
            saveDeviceDependingOfType(deviceToSave)
            getDevice(deviceToSave.id)!!
        }
    }

    private suspend fun saveDeviceDependingOfType(device: Device) {
        when (device.productType) {
            ProductType.HEATER -> heaterDao.insert(device as Heater)
            ProductType.LIGHT -> lightDao.insert(device as Light)
            ProductType.ROLLER_SHUTTER -> rollerShutterDao.insert(device as RollerShutter)
        }
    }
}
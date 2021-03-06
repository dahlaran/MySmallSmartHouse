package com.dahlaran.mysmallsmarthouse.data

import com.dahlaran.mysmallsmarthouse.data.database.DeviceDao
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.utils.FileUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *  Important: Remote Data are statics, so database is always priority.
 *  If there is value inside the db, don't call remote data
 */
class DeviceRepository constructor(private var deviceDao: DeviceDao) {

    suspend fun getDeviceList(): Flow<DataState<List<Device>>> {
        return flow {
            emit(DataState.Loading)
            try {
                // Get data from database
                var deviceList = deviceDao.getDevices()
                if (deviceList.isEmpty()) {
                    deviceDao.addAllDevices(FileUtils.getHouseFromFile().deviceList)

                    // Get data from database (shown information need to be the reflect of the db)
                    deviceList = deviceDao.getDevices()
                }
                emit(DataState.Success(deviceList))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
    }

    suspend fun removeDevice(device: Device): Flow<DataState<List<Device>>> {
        return flow {
            emit(DataState.Loading)
            try {
                deviceDao.removeDevice(device.id)

                // Get data from database
                var deviceList = deviceDao.getDevices()

                // Send new deviceList
                emit(DataState.Success(deviceList))

            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
    }

    suspend fun resetDatabase(): Flow<DataState<List<Device>>> {
        return flow {
            emit(DataState.Loading)
            try {
                deviceDao.removeAllDevices()
                deviceDao.addAllDevices(FileUtils.getHouseFromFile().deviceList)

                // Get data from database (shown information need to be the reflect of the db)
                var deviceList = deviceDao.getDevices()
                emit(DataState.Success(deviceList))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
    }

    suspend fun getDevice(deviceId: Int): Flow<DataState<Device?>>{
        return flow {
            emit(DataState.Loading)
            try {
                val device = deviceDao.getDevice(deviceId)
                emit(DataState.Success(device))
            }
            catch (e: Exception){
                emit(DataState.Error(e))
            }
        }
    }

    suspend fun saveDevice(deviceToSave: Device): Flow<DataState<Device?>> {
        return flow {
            emit(DataState.Loading)
            try {
                val device = deviceDao.saveDevice(deviceToSave)
                emit(DataState.Success(device))
            }
            catch (e: Exception){
                emit(DataState.Error(e))
            }
        }
    }
}
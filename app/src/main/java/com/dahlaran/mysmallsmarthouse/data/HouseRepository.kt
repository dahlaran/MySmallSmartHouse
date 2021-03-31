package com.dahlaran.mysmallsmarthouse.data

import com.dahlaran.mysmallsmarthouse.data.database.UserDao
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.models.User
import com.dahlaran.mysmallsmarthouse.utils.FileUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Repository that take user and listofDevice
 * TODO: make 2 separate repository for a better understanding
 *
 * Important: Remote Data are statics, so database is always priority.
 * If there is value inside the db, don't call remote data
 */
class HouseRepository constructor(private var userDao: UserDao) {

    private var user: User? = null

    private var deviceList: List<Device> = mutableListOf()

    suspend fun getDeviceList(): Flow<DataState<List<Device>>> {
        return flow {
            emit(DataState.Loading)
            try {
                // Use data already saved
                if (deviceList.isNotEmpty()) {
                    emit(DataState.Success(deviceList))
                }
                deviceList = FileUtils.getHouseFromFile().deviceList
                emit(DataState.Success(deviceList))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
    }

    suspend fun getUser(): Flow<DataState<User>> {
        return flow {
            emit(DataState.Loading)
            try {
                // Use data already saved
                if (user != null) {
                    emit(DataState.Success(user!!))
                }
                // Get data from database
                user = userDao.getUser()
                if (user != null) {
                    emit(DataState.Success(user!!))
                } else {
                    // If no data, get data from remote
                    user = FileUtils.getHouseFromFile().user
                    // Not null, then save it inside db
                    user?.run {
                        // only 1 user can be inside the db, so remove all users
                        userDao.deleteAllUsers()
                        userDao.insert(this)
                    }
                    // Get data from database
                    user = userDao.getUser()
                    emit(DataState.Success(user!!))
                }

            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }
    }

    suspend fun saveUser(newUser: User): Flow<DataState<User>> {
        return flow {
            emit(DataState.Loading)
            try {
                userDao.deleteAllUsers()
                userDao.insert(newUser)
                user = userDao.getUser()
                emit(DataState.Success(user!!))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    }
}
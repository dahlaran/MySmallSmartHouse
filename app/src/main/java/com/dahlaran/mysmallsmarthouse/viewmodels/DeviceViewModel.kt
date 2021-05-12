package com.dahlaran.mysmallsmarthouse.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dahlaran.mysmallsmarthouse.data.DataState
import com.dahlaran.mysmallsmarthouse.data.DeviceRepository
import com.dahlaran.mysmallsmarthouse.models.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(val repository: DeviceRepository) : ViewModel() {

    val device: MutableLiveData<Device?> = MutableLiveData()
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getDeviceById(deviceId: Int) {
        if (dataLoading.value != true) {
            val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
            coroutineScope.launch {
                repository.getDevice(deviceId).onEach {
                    when (it) {
                        is DataState.Loading -> {
                            dataLoading.postValue(true)
                        }
                        is DataState.Error -> {  // onError
                            it.exception.printStackTrace()
                            dataLoading.postValue(false)
                        }
                        is DataState.Success<Device?> -> {
                            device.postValue(it.data)
                            dataLoading.postValue(false)
                        }
                    }
                }.launchIn(coroutineScope)
            }
        }
    }

    fun saveDevice(deviceToSave: Device) {
        val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
        coroutineScope.launch {
            repository.saveDevice(deviceToSave).onEach {
                when (it) {
                    is DataState.Loading -> {
                        dataLoading.postValue(true)
                    }
                    is DataState.Error -> {  // onError
                        it.exception.printStackTrace()
                        dataLoading.postValue(false)
                    }
                    is DataState.Success<Device?> -> {
                        device.postValue(it.data)
                        dataLoading.postValue(false)
                    }
                }
            }.launchIn(coroutineScope)
        }
    }
}
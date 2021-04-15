package com.dahlaran.mysmallsmarthouse.viewmodels

import androidx.lifecycle.*
import com.dahlaran.mysmallsmarthouse.data.DataState
import com.dahlaran.mysmallsmarthouse.data.DeviceRepository
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.models.ProductType
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(private val repository: DeviceRepository) :
    ViewModel() {

    // Save Observables to remove when complete or viewModel is destroyed
    private val disposable = CompositeDisposable()

    val devicesList: MutableLiveData<List<Device>> = MutableLiveData()
    val devicesListShow: MutableLiveData<List<Device>> = MutableLiveData()
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()

    val deviceTypeToShow: MutableLiveData<List<ProductType>> = MutableLiveData()

    val empty: LiveData<Boolean> = devicesList.map { it.isEmpty() }

    private val deviceTypeToShowObserver: Observer<List<ProductType>> = Observer{
        filterByTypes(it)
    }

    init {
        updateDeviceListInformation()
        deviceTypeToShow.observeForever(deviceTypeToShowObserver)
    }

    fun updateDeviceListInformation() {
        if (dataLoading.value != true) {
            dataLoading.value = true
            val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
            coroutineScope.launch {
                repository.getDeviceList().onEach {
                    when (it) {
                        is DataState.Loading -> dataLoading.postValue(true)
                        is DataState.Error -> {
                            it.exception.printStackTrace()
                            dataLoading.postValue(false)
                        }
                        is DataState.Success -> {
                            devicesList.postValue(it.data)
                            filterByTypes(deviceTypeToShow.value)
                            dataLoading.postValue(false)
                        }
                    }
                }.launchIn(coroutineScope)
            }
        }
    }


    override fun onCleared() {
        // Remove observable if viewModel is destroyed
        disposable.dispose()
        deviceTypeToShow.removeObserver(deviceTypeToShowObserver)
        super.onCleared()
    }

    fun newDeviceTypeFilter(types: List<ProductType>?) {
        deviceTypeToShow.postValue(types)
    }

    fun resetDeviceList() {
        if (dataLoading.value != true) {
            dataLoading.value = true

            val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
            coroutineScope.launch {
                repository.resetDatabase().onEach {
                    when (it) {
                        is DataState.Loading -> dataLoading.postValue(true)
                        is DataState.Error -> {
                            it.exception.printStackTrace()
                            dataLoading.postValue(false)
                        }
                        is DataState.Success -> {
                            devicesList.postValue(it.data)
                            devicesListShow.postValue(it.data)
                            dataLoading.postValue(false)
                        }
                    }
                }.launchIn(coroutineScope)
            }
        }
    }

    fun removeDevice(device: Device){
        if (dataLoading.value != true){
            dataLoading.value = true

            val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
            coroutineScope.launch {
                repository.removeDevice(device).onEach {
                    when (it) {
                        is DataState.Loading -> dataLoading.postValue(true)
                        is DataState.Error -> {
                            it.exception.printStackTrace()
                            dataLoading.postValue(false)
                        }
                        is DataState.Success -> {
                            devicesList.postValue(it.data)
                            devicesListShow.postValue(it.data)
                            dataLoading.postValue(false)
                        }
                    }
                }.launchIn(coroutineScope)
            }
        }
    }

    private fun filterByTypes(types: List<ProductType>?) {
        val listFiltered = types?.let {
            devicesList.value?.filter { device ->
                it.contains(device.productType)
            }
        } ?: devicesList.value
        devicesListShow.postValue(listFiltered)
    }
}
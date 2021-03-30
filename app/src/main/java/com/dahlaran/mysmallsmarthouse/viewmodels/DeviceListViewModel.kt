package com.dahlaran.mysmallsmarthouse.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.dahlaran.mysmallsmarthouse.data.DataState
import com.dahlaran.mysmallsmarthouse.data.HouseRepository
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
class DeviceListViewModel @Inject constructor(private val repository: HouseRepository) :
    ViewModel() {

    // Save Observables to remove when complete or viewModel is destroyed
    private val disposable = CompositeDisposable()

    val devicesList: MutableLiveData<List<Device>> = MutableLiveData()
    val devicesListShow: MutableLiveData<List<Device>> = MutableLiveData()
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()

    val empty: LiveData<Boolean> = devicesList.map { it.isEmpty() }
    private var savedDeviceList: List<Device>? = null

    init {
        updateHouseInformation()
    }

    fun updateHouseInformation() {
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
                            devicesListShow.postValue(it.data)
                            // Copy value to have a make a sorting inside the liveData
                            savedDeviceList = ArrayList(it.data)
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
        super.onCleared()
    }


    fun filterByTypes(types: List<ProductType>) {
        val listFiltered = devicesList.value?.filter { device ->
            types.contains(device.productType)
        }
        devicesListShow.postValue(listFiltered)
    }
}
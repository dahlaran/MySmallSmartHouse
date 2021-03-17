package com.dahlaran.mysmallsmarthouse.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.dahlaran.mysmallsmarthouse.data.HouseRepository
import com.dahlaran.mysmallsmarthouse.models.Device
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DeviceListViewModel : ViewModel() {

    // Save Observables to remove when complete or viewModel is destroyed
    private val disposable = CompositeDisposable()

    val devicesList: MutableLiveData<List<Device>> = MutableLiveData()
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()

    val empty: LiveData<Boolean> = devicesList.map { it.isEmpty() }
    private var savedDeviceList: List<Device>? = null

    init {
        if (dataLoading.value != true) {
            dataLoading.value = true
            val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
            coroutineScope.launch {
                disposable.add(HouseRepository.getHouseInformation()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { // onNext
                            devicesList.postValue(it.deviceList)
                            // Copy value to have a make a sorting inside the liveData
                            savedDeviceList = ArrayList(it.deviceList)
                        },
                        { // onError
                            it.printStackTrace()
                            dataLoading.postValue(false)
                        },
                        { // onComplete
                            dataLoading.postValue(false)
                        }
                    ))
            }
        }
    }

    fun updateHouseInformation() {
        if (dataLoading.value != true) {
            dataLoading.value = true
            val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
            coroutineScope.launch {
                disposable.add(HouseRepository.updateHouseInformation()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { // onNext
                            devicesList.postValue(it.deviceList)
                            // Copy value to have a make a sorting inside the liveData
                            savedDeviceList = ArrayList(it.deviceList)
                        },
                        { // onError
                            it.printStackTrace()
                            dataLoading.postValue(false)
                        },
                        { // onComplete
                            dataLoading.postValue(false)
                        }
                    ))
            }
        }
    }


    override fun onCleared() {
        // Remove observable if viewModel is destroyed
        disposable.dispose()
        super.onCleared()
    }

}
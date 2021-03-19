package com.dahlaran.mysmallsmarthouse.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dahlaran.mysmallsmarthouse.data.HouseRepository
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.models.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    val user: MutableLiveData<User> = MutableLiveData()
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()

    // Save Observables to remove when complete or viewModel is destroyed
    private val disposable = CompositeDisposable()

    init {
        if (dataLoading.value != true) {
            dataLoading.value = true
            val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
            coroutineScope.launch {
                disposable.add(
                    HouseRepository.getHouseInformation()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { // onNext
                            user.postValue(it.user)
                            // Copy value to have a make a sorting inside the liveData
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
        disposable.clear()
        super.onCleared()
    }
}
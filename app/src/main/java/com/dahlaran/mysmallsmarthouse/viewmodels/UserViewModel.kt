package com.dahlaran.mysmallsmarthouse.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dahlaran.mysmallsmarthouse.data.DataState
import com.dahlaran.mysmallsmarthouse.data.HouseRepository
import com.dahlaran.mysmallsmarthouse.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val repository: HouseRepository) : ViewModel() {

    val user: MutableLiveData<User> = MutableLiveData()
    val dataLoading: MutableLiveData<Boolean> = MutableLiveData()

    init {
        getUser()
    }

    fun getUser() {
        if (dataLoading.value != true) {
            dataLoading.value = true
            val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
            coroutineScope.launch {
                repository.getUser().onEach {
                    when (it) {
                        is DataState.Loading -> dataLoading.postValue(true)
                        is DataState.Error -> {  // onError
                            it.exception.printStackTrace()
                            dataLoading.postValue(false)
                        }
                        is DataState.Success<User> -> {
                            user.postValue(it.data)
                            dataLoading.postValue(false)
                        }
                    }
                }.launchIn(coroutineScope)
            }
        }
    }

    fun saveUser(userToSave: User) {
        val coroutineScope = CoroutineScope(Dispatchers.Default + Job())
        coroutineScope.launch {
            repository.saveUser(userToSave).onEach {
                when (it) {
                    is DataState.Loading -> dataLoading.postValue(true)
                    is DataState.Error -> {  // onError
                        it.exception.printStackTrace()
                        dataLoading.postValue(false)
                    }
                    is DataState.Success<User> -> {
                        user.postValue(it.data)
                        dataLoading.postValue(false)
                    }
                }
            }.launchIn(coroutineScope)
        }
    }
}
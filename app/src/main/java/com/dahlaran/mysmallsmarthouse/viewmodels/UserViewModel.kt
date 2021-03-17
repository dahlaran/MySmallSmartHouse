package com.dahlaran.mysmallsmarthouse.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.models.User

class UserViewModel : ViewModel() {

    val user: MutableLiveData<User> = MutableLiveData()

}
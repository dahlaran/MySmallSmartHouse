package com.dahlaran.mysmallsmarthouse.view.fragments

import androidx.fragment.app.Fragment
import com.dahlaran.mysmallsmarthouse.models.Device

abstract class MainDeviceDescriptionFragment: Fragment() {
    protected var device: Device? = null

    abstract fun createDeviceUsingUI(): Device?

    fun setNewDevice(newDevice: Device) {
        device = newDevice
    }
}
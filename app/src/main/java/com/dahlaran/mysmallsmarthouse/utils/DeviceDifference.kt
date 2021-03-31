package com.dahlaran.mysmallsmarthouse.utils

import androidx.recyclerview.widget.DiffUtil
import com.dahlaran.mysmallsmarthouse.models.Device

class DeviceDifference: DiffUtil.ItemCallback<Device>() {
    override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem == newItem
    }
}
package com.dahlaran.mysmallsmarthouse.view.extension

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.view.DeviceListAdapter

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Any>?) {
    items?.let {
        when (listView.adapter) {
            is DeviceListAdapter ->
                (listView.adapter as DeviceListAdapter).submitList(items as List<Device>?)
        }
    }
}
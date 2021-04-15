package com.dahlaran.mysmallsmarthouse.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dahlaran.mysmallsmarthouse.R
import com.dahlaran.mysmallsmarthouse.models.*
import com.dahlaran.mysmallsmarthouse.utils.DeviceDifference

class DeviceListAdapter(private val onclickItemCallback: (itemClicked: Device, type: ClickedType) -> Unit) :
    ListAdapter<Device, DeviceListAdapter.DeviceViewHolder>(DeviceDifference()) {

    companion object {
        private const val LIGHT = 1
        private const val HEATER = 2
        private const val ROLLER_SHUTTER = 3
    }

    class DeviceViewHolder(
        private val binding: View,
        private val layoutType: Int,
        private val onclickCallback: ((itemClicked: Device, type: ClickedType) -> Unit)?
    ) : RecyclerView.ViewHolder(binding) {

        fun bind(device: Device) {
            // Set information to layout
            // Set on click listener to layout to trigger an event
            itemView.setOnClickListener {
                onclickCallback?.run {
                    this(device, ClickedType.ITEM)
                }
            }

            when (layoutType) {
                LIGHT -> setLightLayout(device as Light)
                HEATER -> setHeaterLayout(device as Heater)
                ROLLER_SHUTTER -> setRollerShutterLayout(device as RollerShutter)
            }


            binding.findViewById<ImageView>(R.id.deviceDeleteImage).setOnClickListener {
                onclickCallback?.run {
                    this(device, ClickedType.DELETE)
                }
            }
        }

        private fun setLightLayout(light: Light) {
            binding.findViewById<TextView>(R.id.nameLightLayout)?.text = light.name
            binding.findViewById<TextView>(R.id.modeLightLayout)?.text = light.mode
            binding.findViewById<TextView>(R.id.intensityLightLayout)?.text =
                light.intensity.toString()
        }

        private fun setHeaterLayout(heater: Heater) {
            binding.findViewById<TextView>(R.id.nameHeaterLayout)?.text = heater.name
            binding.findViewById<TextView>(R.id.modeHeaterLayout)?.text = heater.mode
            binding.findViewById<TextView>(R.id.temperatureHeaterLayout)?.text =
                heater.temperature.toString()
        }

        private fun setRollerShutterLayout(rollerShutter: RollerShutter) {
            binding.findViewById<TextView>(R.id.nameRollerShutterLayout)?.text = rollerShutter.name
            binding.findViewById<TextView>(R.id.positionRollerShutterLayout)?.text =
                rollerShutter.position.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        val device = getItem(position)
        return when (device.productType) {
            ProductType.LIGHT -> LIGHT
            ProductType.HEATER -> HEATER
            ProductType.ROLLER_SHUTTER -> ROLLER_SHUTTER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = when (viewType) {
            LIGHT -> layoutInflater.inflate(R.layout.layout_device, parent, false)
            HEATER -> layoutInflater.inflate(R.layout.layout_heater, parent, false)
            ROLLER_SHUTTER -> layoutInflater.inflate(R.layout.layout_roller_shutter, parent, false)
            else -> layoutInflater.inflate(R.layout.layout_device, parent, false)
        }
        return DeviceViewHolder(binding, viewType, onclickItemCallback)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

enum class ClickedType {
    ITEM, DELETE
}

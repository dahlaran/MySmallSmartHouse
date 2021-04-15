package com.dahlaran.mysmallsmarthouse.view.customs

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dahlaran.mysmallsmarthouse.R
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.models.ProductType
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class DeviceChipGroup : ChipGroup {
    var selectedItem: MutableList<ProductType> = mutableListOf()

    private var liveDataWeakReference: LiveData<List<Device>>? = null
    private val itemList: MutableList<ProductType> = mutableListOf()
    private var selectedListener: ((List<ProductType>) -> Unit)? = null

    // Observe list
    private val observer: Observer<List<Device>> = Observer { list ->
        val deviceTypeNew = mutableListOf<ProductType>()
        list.forEach { device ->
            // Not found inside the list of type then add it
            if (deviceTypeNew.find { device.productType == it } == null) {
                deviceTypeNew.add(device.productType)
            }
        }

        // Check if there is a difference between the type of device
        if (deviceTypeNew.count() != itemList.count() || !itemList.containsAll(deviceTypeNew)) {
            // Remove all type that is not inside the device list
            val tmpList = itemList.filter { type -> (deviceTypeNew.find { type == it } != null) }
            itemList.clear()
            itemList.addAll(tmpList)
            // Add new device type that is not already inside the itemList
            deviceTypeNew.forEach { type ->
                // Not found inside the chip list then add it
                if (itemList.find { type == it } == null) {
                    itemList.add(type)
                }
            }

            // If there is no item selected, update selected items by the list of items (enable all)
            if (selectedItem.isEmpty()) {
                selectedItem = itemList.toMutableList()
            }

            // TODO: remove only the new one
            removeAllViews()
            itemList.forEach {
                drawShip(it)
            }

            // After draw, signal the listener all selected items (sure to have the same information)
            selectedListener?.invoke(selectedItem)
        }
    }

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    fun setOnSelectedListener(onChipSelected: (listItem: List<ProductType>) -> Unit) {
        selectedListener = onChipSelected
    }

    fun setLiveDataToListen(liveData: MutableLiveData<List<Device>>) {
        liveData.observeForever(observer)
    }

    private fun drawShip(productType: ProductType) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val chip = inflater.inflate(R.layout.device_type_chip, this, false) as Chip

        // Set chip properties
        chip.apply {
            isCheckable = true
            isChecked = selectedItem.find { productType == it } != null
            when (productType) {
                ProductType.LIGHT -> {
                    tag = ProductType.LIGHT.type
                    text = context.getString(R.string.light)
                    chipIcon = AppCompatResources.getDrawable(context, R.drawable.ic_light_image)
                }
                ProductType.HEATER -> {
                    tag = ProductType.HEATER.type
                    text = context.getString(R.string.heater)
                    chipIcon = AppCompatResources.getDrawable(context, R.drawable.ic_heater_image)
                }
                ProductType.ROLLER_SHUTTER -> {
                    tag = ProductType.ROLLER_SHUTTER.type
                    text = context.getString(R.string.roller_shutter)
                    chipIcon = AppCompatResources.getDrawable(context, R.drawable.ic_roller_shutter)
                }
            }
        }
        // Set listener
        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                enableItem(productType)
            } else {
                disableItem(productType)
            }
            selectedListener?.invoke(selectedItem)
        }

        // Draw chip
        addView(chip)
    }

    private fun enableItem(productType: ProductType) {
        if (selectedItem.find { productType == it } == null) {
            selectedItem.add(productType)
        }
    }

    private fun disableItem(productType: ProductType) {
        if (selectedItem.find { productType == it } != null) {
            selectedItem.remove(productType)
        }
    }

    override fun onDetachedFromWindow() {
        liveDataWeakReference?.removeObserver(observer)
        super.onDetachedFromWindow()
    }
}
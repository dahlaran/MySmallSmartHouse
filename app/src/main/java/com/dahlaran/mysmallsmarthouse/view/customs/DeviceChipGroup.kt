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

    private var liveDataWeakReference: LiveData<List<Device>>? = null
    private val observer: Observer<List<Device>> = Observer { list ->
        itemList.clear()
        list.forEach { device ->
            if (itemList.find { device.productType == it } == null) {
                itemList.add(device.productType)
            }
        }
        selectedItem = itemList.toMutableList()
        itemList.forEach {
            drawShip(it)
        }
    }

    private val itemList: MutableList<ProductType> = mutableListOf()
    private var selectedItem: MutableList<ProductType> = mutableListOf()
    private var selectedListener: ((List<ProductType>) -> Unit)? = null


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

        chip.apply {/*
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )*/

            isCheckable = true
            isChecked = true
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
        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                enableItem(productType)
            } else {
                disableItem(productType)
            }
            selectedListener?.invoke(selectedItem)
        }

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
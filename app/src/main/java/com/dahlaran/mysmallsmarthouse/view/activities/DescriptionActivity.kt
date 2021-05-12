package com.dahlaran.mysmallsmarthouse.view.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.dahlaran.mysmallsmarthouse.R
import com.dahlaran.mysmallsmarthouse.databinding.ActivityDeviceDescriptionBinding
import com.dahlaran.mysmallsmarthouse.models.ProductType
import com.dahlaran.mysmallsmarthouse.utils.ErrorMessenger
import com.dahlaran.mysmallsmarthouse.view.ExtraConst
import com.dahlaran.mysmallsmarthouse.view.fragments.DeviceDescriptionHeaterFragment
import com.dahlaran.mysmallsmarthouse.view.fragments.DeviceDescriptionLightFragment
import com.dahlaran.mysmallsmarthouse.view.fragments.DeviceDescriptionRollerShutterFragment
import com.dahlaran.mysmallsmarthouse.view.fragments.MainDeviceDescriptionFragment
import com.dahlaran.mysmallsmarthouse.viewmodels.DeviceViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeviceDescriptionBinding
    private val deviceViewModel: DeviceViewModel by viewModels()
    private var descriptionFragment: MainDeviceDescriptionFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_description)
        binding = ActivityDeviceDescriptionBinding.inflate(layoutInflater)

        deviceViewModel.device.observe(this@DescriptionActivity, Observer { device ->
            if (device != null) {
                if (descriptionFragment == null) {
                    descriptionFragment = when (device.productType) {
                        ProductType.HEATER -> DeviceDescriptionHeaterFragment()
                        ProductType.LIGHT -> DeviceDescriptionLightFragment()
                        ProductType.ROLLER_SHUTTER -> DeviceDescriptionRollerShutterFragment()
                    }
                    supportFragmentManager.beginTransaction().add(
                        R.id.descriptionFragmentContainer,
                        descriptionFragment!!
                    ).commit()
                }
                descriptionFragment!!.apply {
                    setNewDevice(device)
                }
            }
        })

        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val deviceId = intent.getIntExtra(ExtraConst.EXTRA_DEVICE_ID, 0)
        deviceViewModel.getDeviceById(deviceId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.device_desciption_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.saveDescriptionMenu -> {
                val device = descriptionFragment?.createDeviceUsingUI()
                if (device != null) {
                    deviceViewModel.saveDevice(device)
                } else {
                    ErrorMessenger.errorMessageToShow(
                        javaClass.name,
                        R.string.error_field_with_error
                    )
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
package com.dahlaran.mysmallsmarthouse.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dahlaran.mysmallsmarthouse.R
import com.dahlaran.mysmallsmarthouse.databinding.FragmentDescriptionHeaterBinding
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.models.Heater
import com.triggertrap.seekarc.SeekArc
import com.triggertrap.seekarc.SeekArc.OnSeekArcChangeListener

class DeviceDescriptionHeaterFragment : MainDeviceDescriptionFragment() {

    private lateinit var viewDataBinding: FragmentDescriptionHeaterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewDataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_description_heater,
                container,
                false
            )
        viewDataBinding.apply {
            this.heater = device!! as Heater
            this.lifecycleOwner = viewLifecycleOwner
        }

        viewDataBinding.seekArcTemperature.setOnSeekArcChangeListener(object :
            OnSeekArcChangeListener {
            override fun onProgressChanged(p0: SeekArc?, p1: Int, p2: Boolean) {
                viewDataBinding.heaterTemperatureText.text = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekArc?) {
            }

            override fun onStopTrackingTouch(p0: SeekArc?) {
            }
        })

        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun createDeviceUsingUI(): Device? {
        return Heater(
            device!!.id,
            device!!.name,
            Heater.convertStatusToMode(viewDataBinding.switchCompatStatus.isChecked),
            device!!.productType,
            viewDataBinding.seekArcTemperature.progress
        )
    }
}
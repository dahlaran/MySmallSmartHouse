package com.dahlaran.mysmallsmarthouse.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dahlaran.mysmallsmarthouse.R
import com.dahlaran.mysmallsmarthouse.databinding.FragmentDeviceRollerShutterBinding
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.models.RollerShutter
import com.triggertrap.seekarc.SeekArc

class DeviceDescriptionRollerShutterFragment : MainDeviceDescriptionFragment() {
    private lateinit var viewDataBinding: FragmentDeviceRollerShutterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewDataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_device_roller_shutter,
                container,
                false
            )
        viewDataBinding.apply {
            this.rollerShutter = device!! as RollerShutter
            this.lifecycleOwner = viewLifecycleOwner
        }

        viewDataBinding.seekArcPosition.setOnSeekArcChangeListener(object :
            SeekArc.OnSeekArcChangeListener {
            override fun onProgressChanged(p0: SeekArc?, p1: Int, p2: Boolean) {
                viewDataBinding.rollerShutterPositionText.text = p1.toString()
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
        return RollerShutter(
            device!!.id,
            device!!.name,
            viewDataBinding.rollerShutterPositionText.text.toString().toInt(),
            device!!.productType
        )
    }
}
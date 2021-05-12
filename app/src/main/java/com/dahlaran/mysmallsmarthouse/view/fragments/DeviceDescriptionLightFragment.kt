package com.dahlaran.mysmallsmarthouse.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.dahlaran.mysmallsmarthouse.R
import com.dahlaran.mysmallsmarthouse.databinding.FragmentDescpriptionLightBinding
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.models.Light
import com.triggertrap.seekarc.SeekArc

class DeviceDescriptionLightFragment : MainDeviceDescriptionFragment() {
    private lateinit var viewDataBinding: FragmentDescpriptionLightBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewDataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_descpription_light,
                container,
                false
            )
        viewDataBinding.apply {
            this.light = device!! as Light
            this.lifecycleOwner = viewLifecycleOwner
        }

        viewDataBinding.seekArcIntensity.setOnSeekArcChangeListener(object :
            SeekArc.OnSeekArcChangeListener {
            override fun onProgressChanged(p0: SeekArc?, p1: Int, p2: Boolean) {
                viewDataBinding.lightIntensityText.text = p1.toString()
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
        return Light(
            device!!.id,
            device!!.name,
            viewDataBinding.lightIntensityText.text.toString().toInt(),
            Light.convertStatusToMode(viewDataBinding.switchCompatStatus.isChecked),
            device!!.productType
        )
    }

}
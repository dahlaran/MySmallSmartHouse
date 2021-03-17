package com.dahlaran.mysmallsmarthouse.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dahlaran.mysmallsmarthouse.R
import com.dahlaran.mysmallsmarthouse.databinding.FragmentDeviceListBinding
import com.dahlaran.mysmallsmarthouse.utils.CustomLogger
import com.dahlaran.mysmallsmarthouse.view.DeviceListAdapter
import com.dahlaran.mysmallsmarthouse.viewmodels.DeviceListViewModel

class DeviceListFragment : Fragment(){
    private val mediaListViewModel: DeviceListViewModel by activityViewModels()
    private lateinit var viewDataBinding: FragmentDeviceListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_device_list, container, false)
        viewDataBinding.apply {
            this.viewmodel = mediaListViewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListAdapter()
        setDeviceTypeFilter()
    }

    private fun setUpListAdapter() {
        val listAdapter = DeviceListAdapter { itemClicked ->
            CustomLogger.logD(javaClass.name, "device = "+ itemClicked.name)
        }

        viewDataBinding.mediaListRecycler.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setDeviceTypeFilter() {
        viewDataBinding.filterDeviceType.setLiveDataToListen(mediaListViewModel.devicesList)
        viewDataBinding.filterDeviceType.setOnSelectedListener {
            mediaListViewModel.filterByTypes(it)
        }
    }
}
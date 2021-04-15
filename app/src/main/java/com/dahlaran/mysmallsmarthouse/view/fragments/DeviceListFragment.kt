package com.dahlaran.mysmallsmarthouse.view.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.dahlaran.mysmallsmarthouse.R
import com.dahlaran.mysmallsmarthouse.databinding.FragmentDeviceListBinding
import com.dahlaran.mysmallsmarthouse.models.Device
import com.dahlaran.mysmallsmarthouse.utils.CustomLogger
import com.dahlaran.mysmallsmarthouse.view.ClickedType
import com.dahlaran.mysmallsmarthouse.view.DeviceListAdapter
import com.dahlaran.mysmallsmarthouse.viewmodels.DeviceListViewModel

class DeviceListFragment : Fragment() {
    private val deviceListViewModel: DeviceListViewModel by activityViewModels()
    private lateinit var viewDataBinding: FragmentDeviceListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_device_list, container, false)
        viewDataBinding.apply {
            this.viewmodel = deviceListViewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.device_list_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deviceListResetMenu -> {
                deviceListViewModel.resetDeviceList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListAdapter()
        setDeviceTypeFilter()
    }

    private fun setUpListAdapter() {
        val listAdapter = DeviceListAdapter { itemClicked, type ->
            CustomLogger.logD(javaClass.name, "device = " + itemClicked.name + " type = " + type)
            when (type) {
                ClickedType.DELETE -> deviceListViewModel.removeDevice(itemClicked)
            }
        }

        viewDataBinding.deviceListRecycler.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setDeviceTypeFilter() {
        viewDataBinding.filterDeviceType.setLiveDataToListen(deviceListViewModel.devicesList)
        viewDataBinding.filterDeviceType.setOnSelectedListener {
            deviceListViewModel.newDeviceTypeFilter(it)
        }
    }
}
package com.dahlaran.mysmallsmarthouse.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dahlaran.mysmallsmarthouse.R
import com.dahlaran.mysmallsmarthouse.databinding.FragmentUserBinding
import com.dahlaran.mysmallsmarthouse.viewmodels.UserViewModel

class UserFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var viewDataBinding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false)
        viewDataBinding.apply {
            this.viewmodel = userViewModel
            this.lifecycleOwner = viewLifecycleOwner
        }
        return viewDataBinding.root
    }

}
package com.dahlaran.mysmallsmarthouse.view.fragments

import android.os.Bundle
import android.view.*
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.dahlaran.mysmallsmarthouse.R
import com.dahlaran.mysmallsmarthouse.databinding.FragmentUserBinding
import com.dahlaran.mysmallsmarthouse.models.Address
import com.dahlaran.mysmallsmarthouse.models.User
import com.dahlaran.mysmallsmarthouse.utils.DateUtils
import com.dahlaran.mysmallsmarthouse.utils.EditTextUtils
import com.dahlaran.mysmallsmarthouse.utils.ErrorMessenger
import com.dahlaran.mysmallsmarthouse.view.customs.DateInputMask
import com.dahlaran.mysmallsmarthouse.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_user.*


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

        setEditTextWatcher()
        setEditTextListener()
        userViewModel.getUser()
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.saveUserMenu -> {
                if (checkIfThereIsNoError()) {
                    userViewModel.saveUser(createUserUsingUI())
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

    private fun setEditTextWatcher() {
        DateInputMask(viewDataBinding.birthDateUserEdit)
    }

    private fun setEditTextListener() {
        viewDataBinding.firstNameUserEdit.addTextChangedListener { editable ->
            val errorMessage: String? =
                if (editable == null) {
                    getString(R.string.error_field_unknown)
                } else {
                    val verification = EditTextUtils.noNumberTextVerifier(editable.toString())
                    if (verification != 0) {
                        getString(verification)
                    } else {
                        null
                    }
                }
            viewDataBinding.firstNameUserEdit.error = errorMessage
        }

        viewDataBinding.lastNameUserEdit.addTextChangedListener { editable ->
            val errorMessage: String? =
                if (editable == null) {
                    getString(R.string.error_field_unknown)
                } else {
                    val verification = EditTextUtils.noNumberTextVerifier(editable.toString())
                    if (verification != 0) {
                        getString(verification)
                    } else {
                        null
                    }
                }
            viewDataBinding.lastNameUserEdit.error = errorMessage
        }

        viewDataBinding.birthDateUserEdit.addTextChangedListener { editable ->
            val errorMessage: String? =
                if (editable == null) {
                    getString(R.string.error_field_unknown)
                }
                // check if the date length is respected (dd/mm/YYYY) = 10
                else if (editable.length != 10) {
                    return@addTextChangedListener
                } else {
                    val verification = EditTextUtils.onlyNumberTextVerifier(editable.toString())
                    if (verification != 0) {
                        getString(verification)
                    } else {
                        null
                    }
                }
            viewDataBinding.birthDateUserEdit.error = errorMessage
        }

        viewDataBinding.cityUserEdit.addTextChangedListener { editable ->
            val errorMessage: String? =
                if (editable == null) {
                    getString(R.string.error_field_unknown)
                } else {
                    val verification = EditTextUtils.noNumberTextVerifier(editable.toString())
                    if (verification != 0) {
                        getString(verification)
                    } else {
                        null
                    }
                }
            viewDataBinding.cityUserEdit.error = errorMessage
        }

        viewDataBinding.postalCodeUserEdit.addTextChangedListener { editable ->
            val errorMessage: String? =
                if (editable == null) {
                    getString(R.string.error_field_unknown)
                } else {
                    val verification = EditTextUtils.onlyNumberTextVerifier(editable.toString())
                    if (verification != 0) {
                        getString(verification)
                    } else {
                        null
                    }
                }
            viewDataBinding.postalCodeUserEdit.error = errorMessage
        }
        viewDataBinding.streetUserEdit.addTextChangedListener { editable ->
            val errorMessage: String? =
                if (editable == null) {
                    getString(R.string.error_field_unknown)
                } else {
                    val verification = EditTextUtils.noNumberTextVerifier(editable.toString())
                    if (verification != 0) {
                        getString(verification)
                    } else {
                        null
                    }
                }
            viewDataBinding.streetUserEdit.error = errorMessage
        }
        viewDataBinding.streetCodeUserEdit.addTextChangedListener { editable ->
            val errorMessage: String? =
                if (editable == null) {
                    getString(R.string.error_field_unknown)
                } else {
                    val verification = EditTextUtils.maxTextVerifier(editable.toString())
                    if (verification != 0) {
                        getString(verification)
                    } else {
                        null
                    }
                }
            viewDataBinding.streetCodeUserEdit.error = errorMessage
        }

        viewDataBinding.countryUserEdit.addTextChangedListener { editable ->
            val errorMessage: String? =
                if (editable == null) {
                    getString(R.string.error_field_unknown)
                } else {
                    val verification = EditTextUtils.noNumberTextVerifier(editable.toString())
                    if (verification != 0) {
                        getString(verification)
                    } else {
                        null
                    }
                }
            viewDataBinding.countryUserEdit.error = errorMessage
        }
    }

    private fun checkIfThereIsNoError(): Boolean {
        return (city_user_edit.error == null &&
                country_user_edit.error == null &&
                postal_code_user_edit.error == null &&
                street_user_edit.error == null &&
                street_code_user_edit.error == null &&
                birth_date_user_edit.error == null &&
                first_name_user_edit.error == null &&
                last_name_user_edit.error == null)
    }

    private fun createUserUsingUI(): User {
        val address = Address(
            city_user_edit.text.toString(),
            country_user_edit.text.toString(),
            postal_code_user_edit.text.toString().toInt(),
            street_user_edit.text.toString(),
            street_code_user_edit.text.toString())

        return User(
            address,
            DateUtils.convertDateStringToLong(birth_date_user_edit.text.toString()),
            first_name_user_edit.text.toString(),
            last_name_user_edit.text.toString())
    }
}
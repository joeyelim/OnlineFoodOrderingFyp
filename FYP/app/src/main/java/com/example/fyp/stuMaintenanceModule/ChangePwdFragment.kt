package com.example.fyp.stuMaintenanceModule


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentChangePwdBinding


/**
 * A simple [Fragment] subclass.
 */
class ChangePwdFragment : Fragment() {
    private lateinit var binding: FragmentChangePwdBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_change_pwd, container, false
        )

        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.btnConfirm.setOnClickListener(){
            inputValidation()
        }

        return binding.root
    }

    private fun inputValidation(): Boolean {
        val oldPwd = binding.txtOldPwd.text.toString()
        val newPwd = binding.txtNewPwd.text.toString()
        val confirmPwd = binding.txtConfirmPwd.text.toString()

        if (oldPwd.isEmpty() || newPwd.isEmpty() || confirmPwd.isEmpty()) {
            if (oldPwd.isEmpty()) {
                binding.txtOldPwdLayout.error = "*Old password is require."
                binding.txtOldPwdLayout.requestFocus()
            }
            if (newPwd.isEmpty()) {
                binding.txtNewPwdLayout.error = "*New password is require."
                binding.txtNewPwdLayout.requestFocus()
            }
            if (confirmPwd.isEmpty()) {
                binding.txtConfirmPwdLayout.error = "*Confirm password is require."
                binding.txtConfirmPwdLayout.requestFocus()
            }
            return false
        }
        else{
            if (oldPwd != userViewModel.user!!.password.toString()){
                binding.txtOldPwdLayout.error = "*Old password is invalid."
                binding.txtOldPwdLayout.requestFocus()
            }
            if (confirmPwd == newPwd){
                binding.txtConfirmPwdLayout.error = "*Confirm password is invalid."
                binding.txtConfirmPwdLayout.requestFocus()
            }
            return false
        }

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}

package com.example.fyp.LoginModule


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.databinding.FragmentResetPwdBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * A simple [Fragment] subclass.
 */
class ResetPwdFragment : Fragment() {
    private lateinit var binding: FragmentResetPwdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_reset_pwd, container, false
        )
        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.btnBack.setOnClickListener {
            it.findNavController()
                .navigate(ResetPwdFragmentDirections.actionResetPwdFragmentToFragmentForgotPassword())
        }

        binding.btnConfirm.setOnClickListener {
            if (!inputValidation()) {

            } else {
                this.findNavController()
                    .navigate(ResetPwdFragmentDirections.actionResetPwdFragmentToResetSucessfulFragment())


            }

        }

        return binding.root
    }


    private fun inputValidation(): Boolean {
        val newPwd = binding.txtNewPwd.text.toString()
        val confirmPwd = binding.txtConfirmPwd.text.toString()

        if (newPwd.isEmpty() || confirmPwd.isEmpty()) {
            if (newPwd.isEmpty()) {
                binding.txtNewPwdLayout.error = "*New password is require."
                binding.txtNewPwdLayout.requestFocus()
            } else {
                binding.txtNewPwdLayout.isErrorEnabled = false
            }
            if (confirmPwd.isEmpty()) {
                binding.txtConfirmPwdLayout.error = "*Confirm password is require."
                binding.txtConfirmPwdLayout.requestFocus()
            } else if (confirmPwd == newPwd) {
                binding.txtConfirmPwdLayout.error = "*Confirm password is invalid."
                binding.txtConfirmPwdLayout.requestFocus()
            } else {
                binding.txtConfirmPwdLayout.isErrorEnabled = false
            }
        } else {
            return true
        }


        return false
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }


}

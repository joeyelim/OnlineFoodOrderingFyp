package com.example.fyp.stuMaintenanceModule


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentChangePwdBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


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

        binding.btnConfirm.setOnClickListener() {
            inputValidation()

            resetPassword()
        }

        return binding.root
    }

    private fun resetPassword() {
        val user = Firebase.auth.currentUser!!

        val credential = EmailAuthProvider
            .getCredential(userViewModel.user?.email!!, binding.txtOldPwd.text.toString())

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
            .addOnSuccessListener {
                Log.i("Test", "Reauth Sucess")
            }
            .addOnFailureListener {
                Log.i("Test", "Reauth Fail")
            }
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    user.updatePassword(binding.txtNewPwd.text.toString())
                        .addOnSuccessListener {
                            Log.i("Test", "Reset Sucess")
                        }
                        .addOnFailureListener {
                            Log.i("Test", "Reset Fail")
                        }
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val snackbar = Snackbar.make(
                                    this.requireView(), "Password Updated"
                                    , Snackbar.LENGTH_LONG
                                )
                                snackbar.setAction("Close", View.OnClickListener {
                                    snackbar.dismiss()
                                })
                                (snackbar.view).layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                                snackbar.show()
                            }
                        }
                }
            }
    }

    private fun inputValidation(): Boolean {
        val oldPwd = binding.txtOldPwd.text.toString()
        val newPwd = binding.txtNewPwd.text.toString()
        val confirmPwd = binding.txtConfirmPwd.text.toString()

        if (oldPwd.isEmpty() || newPwd.isEmpty() || confirmPwd.isEmpty() ||
            oldPwd != userViewModel.user!!.password.toString() ||
            confirmPwd != newPwd
        ) {

            if (oldPwd.isEmpty()) {
                binding.txtOldPwdLayout.error = "*Old password is require."
            } else {
                binding.txtOldPwdLayout.isErrorEnabled = false
            }

            if (newPwd.isEmpty()) {
                binding.txtNewPwdLayout.error = "*New password is require."
            } else {
                binding.txtNewPwdLayout.isErrorEnabled = false
            }

            if (confirmPwd.isEmpty()) {
                binding.txtConfirmPwdLayout.error = "*Confirm password is require."
            } else if (confirmPwd != newPwd) {
                binding.txtConfirmPwdLayout.error = "*Confirm password is invalid."
                binding.txtConfirmPwdLayout.requestFocus()
            } else {
                binding.txtConfirmPwdLayout.isErrorEnabled = false
            }
            return false
        }

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}

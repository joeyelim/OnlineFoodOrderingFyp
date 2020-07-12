package com.example.fyp.LoginModule


import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
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
import com.example.fyp.databinding.FragmentForgotPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * A simple [Fragment] subclass.
 */
class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_forgot_password, container, false
        )
        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.btnNext.setOnClickListener{
            if (!forgotPasswordValidation()){

            }else
                Firebase.auth.sendPasswordResetEmail(binding.txtForgotPwdEmail.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Test", "Email sent.")
                            val snackbar = Snackbar.make(
                                this.requireView(), "Please Check Your Email To Reset Password"
                                , Snackbar.LENGTH_LONG
                            )
                            snackbar.setAction("Close", View.OnClickListener {
                                snackbar.dismiss()
                            })
                            (snackbar.view).layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                            snackbar.show()
                        }
                    }
//                it.findNavController()
//                    .navigate(ForgotPasswordFragmentDirections.actionFragmentForgotPasswordToResetPwdFragment())

        }

        binding.btnBack.setOnClickListener {
            it.findNavController()
                .navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToFragmentLogin())

        }

        return binding.root
    }

    private fun forgotPasswordValidation():Boolean{
        val email = binding.txtForgotPwdEmail.text.toString()

        if (email.isEmpty()) {
            binding.txtForgotPwdEmailLayout.error = "*Email is require."
            binding.txtForgotPwdEmailLayout.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.txtForgotPwdEmailLayout.error = "*Please enter a valid email"
            binding.txtForgotPwdEmailLayout.requestFocus()
        }
        else {
            binding.txtForgotPwdEmailLayout.isErrorEnabled = false
            return true
        }

       return false
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}

package com.example.fyp.stuMaintenanceModule


import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.fyp.LoginModule.LoginFragment
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.databinding.FragmentLoginBinding
import com.example.fyp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.txtEmail
import kotlinx.android.synthetic.main.fragment_sign_up.txtPassword
import java.util.*
import java.util.regex.Pattern

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sign_up, container, false
        )

        setHasOptionsMenu(true)

        binding.btnLogin.paintFlags = binding.btnLogin.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.btnLogin.setOnClickListener {

            it.findNavController()
                .navigate(SignUpFragmentDirections.actionFragmentSignUpToFragmentLogin())
        }
        binding.containedBtnSignUp.setOnClickListener {

            signUpUser()
        }

        // hide bottom nav
        (activity as MainActivity).bottomNavigationView.visibility = View.GONE

        return binding.root
    }

    private fun signUpUser() {
        if(!validation()) {
            return
        }
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        val firstName = binding.txtFirstN.text.toString()
        val lastName = binding.txtLastN.text.toString()
        val phone = binding.txtPhone.text.toString()

        auth.createUserWithEmailAndPassword(
            txtEmail.text.toString(),
            txtPassword.text.toString()
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Game", "createUserWithEmail:success")
                    Toast.makeText(
                        activity, "Successful create an account",
                        Toast.LENGTH_SHORT
                    ).show()

                    var user = auth.currentUser

                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w("Game", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        activity, "Wrong Email or Email Being Registered",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validation() : Boolean{
        if (binding.txtFirstN.text.toString().isEmpty()) {
            binding.txtFirstN.error = "Please enter value"
            binding.txtFirstN.requestFocus()
            return false
        }
        else if (binding.txtLastN.text.toString().isEmpty()) {
            binding.txtLastN.error = "Please enter value"
            binding.txtLastN.requestFocus()
            return false
        }
        else if (binding.txtPhone.text.toString().isEmpty()) {
            binding.txtPhone.error = "Please enter phone number"
            binding.txtPhone.requestFocus()
            return false
        }
        else if (binding.txtEmail.text.toString().isEmpty()) {
            binding.txtEmail.error = "Please enter email"
            binding.txtEmail.requestFocus()
            return false
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.txtEmail.text.toString()).matches()) {
            binding.txtEmail.error = "Please enter a valid email"
            binding.txtEmail.requestFocus()
            return false
        }

        else if (binding.txtPassword.text.toString().isEmpty()) {
            binding.txtPassword.error = "Please enter password"
            binding.txtPassword.requestFocus()
            return false
        }
        return true
    }


    //clear option menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}

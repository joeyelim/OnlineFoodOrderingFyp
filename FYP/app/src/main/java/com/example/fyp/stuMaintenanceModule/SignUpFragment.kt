package com.example.fyp.stuMaintenanceModule


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.txtEmail
import kotlinx.android.synthetic.main.fragment_sign_up.txtPassword
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

    private fun signUpUser(){
        if(txtFirstN.editText?.text.toString().isEmpty()){
            txtFirstN.error = "Please enter value"
            txtFirstN.requestFocus()
            return
        }
        if(txtLastN.editText?.text.toString().isEmpty()){
            txtLastN.error = "Please enter value"
            txtLastN.requestFocus()
            return
        }
        if(txtPhone.editText?.text.toString().isEmpty()){
            txtPhone.error = "Please enter phone number"
            txtPhone.requestFocus()
            return
        }
        if(txtEmail.editText?.text.toString().isEmpty()){
            txtEmail.error = "Please enter email"
            txtEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(txtEmail.editText?.text.toString()).matches()){
            txtEmail.error = "Please enter a valid email"
            txtEmail.requestFocus()
            return
        }

        if(txtPassword.editText?.text.toString().isEmpty()){
            txtPassword.error = "Please enter password"
            txtPassword.requestFocus()
            return
        }

//        auth.createUserWithEmailAndPassword(txtEmail.editText.toString(), txtPassword.editText.toString())
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    startActivity(Intent(this,LoginFragment::class.java))
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(baseContext, "Authentication failed. Try again after some time.",
//                        Toast.LENGTH_SHORT).show()
//
//                }
//
//
//            }
    }

    //clear option menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}

package com.example.fyp.LoginModule

/*
*   To enable binding
* 1. Cover your layout with <layout> tag 参考 login fragment 的 layout
* 2. private lateinit var 还有 initialize binding (参考下面的 code)
* 3. return binding.root
* 4. 过后你要 target layout 的 control 就可以用 binding.textbox 这样的
*
*
* */

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fyp.MainActivity
import com.example.fyp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.graphics.drawable.GradientDrawable
import android.graphics.Color
import com.example.fyp.R


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()

        // use for binding, remember to set the layout cover by <layout> tag
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_login, container, false
        )

        // option menu
        setHasOptionsMenu(true)

        binding.btnSignUp.paintFlags = binding.btnSignUp.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        (activity as MainActivity).setNavInvisible()

        binding.btnSignUp.setOnClickListener {

            it.findNavController()
                .navigate(LoginFragmentDirections.actionLoginToRegistration())

        }

        binding.btnForgotPassword.setOnClickListener {
            it.findNavController()
                .navigate(LoginFragmentDirections.actionFragmentLoginToForgotPasswordFragment())
        }

        binding.btnLogin.setOnClickListener{
            login()
        }
        return binding.root
    }

    private fun login() {

        if (!validation()) {
            return
        }

        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        binding.btnLogin.isEnabled = false

        Toast.makeText(
            activity, "Logging In... Please Wait",
            Toast.LENGTH_SHORT
        ).show()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Game", "signInWithEmail:success")

                    val user = FirebaseAuth.getInstance().currentUser

                    //findNavController().navigate(com.example.drugassignment.R.id.action_login_to_homeFragment)

                    user?.let {
                        if (!user.isEmailVerified) {
                            FirebaseAuth.getInstance().signOut()
                            binding.btnLogin.isEnabled = true
                            Toast.makeText(
                                activity, "Please Verify Your Email First",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                activity, "Welcome, Directing To Profile Page..",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    binding.btnLogin.isEnabled = true
                    Log.w("Game", "signInWithEmail:failure", task.exception)

                    Toast.makeText(
                        activity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validation() : Boolean {

        if (binding.txtEmail.text.isNullOrBlank() || binding.txtPassword.text.isNullOrBlank()) {
            if(binding.txtEmail.text.isNullOrBlank()) {
                binding.txtEmailLy.error = "*Please enter your email."
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(binding.txtEmail.text.toString()).matches()) {
                binding.txtEmailLy.error = "*Please enter a valid email"
            }
            else {
                binding.txtEmailLy.isErrorEnabled = false
            }

            if (binding.txtPassword.text.isNullOrBlank()) {
                binding.txtPasswordLy.error = "*Please enter your password."
            }
            else {
                binding.txtPasswordLy.isErrorEnabled = false
            }

            return false
        }

        return true
    }


    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    // 拿掉 option menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    // 这个是 display bottom navbar, 当你退出这个 fragment 的时候
    override fun onDestroyView() {
        super.onDestroyView()
//        (activity as MainActivity).setNavVisible()
    }

    fun updateUI(currentUser: FirebaseUser?) {

    }

}

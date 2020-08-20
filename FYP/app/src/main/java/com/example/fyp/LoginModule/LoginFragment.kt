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

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.fyp.Chat.Service.MyFirebaseInstanceIDService
import com.example.fyp.Class.User
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.LoginViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId


/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()

        // use for binding, remember to set the layout cover by <layout> tag
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        // option menu
        setHasOptionsMenu(true)

        binding.btnSignUp.paintFlags = binding.btnSignUp.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        (activity as MainActivity).setNavInvisible()

        loginViewModel = ViewModelProviders.of(activity!!).get(LoginViewModel::class.java)
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)

        binding.btnSignUp.setOnClickListener {
            it.findNavController()
                .navigate(LoginFragmentDirections.actionLoginToRegistration())
        }

        binding.btnForgotPassword.setOnClickListener {
            it.findNavController()
                .navigate(LoginFragmentDirections.actionFragmentLoginToForgotPasswordFragment())
        }

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.textView2.setOnClickListener {
            hideKeyboard()
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

                    val user2 = FirebaseAuth.getInstance().currentUser

                    user2?.let {
                        if (!user2.isEmailVerified) {
                            FirebaseAuth.getInstance().signOut()
                            binding.btnLogin.isEnabled = true
                            Toast.makeText(
                                activity, "Please Verify Your Email First",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            Toast.makeText(
                                activity, "Welcome! Directing To Home Page...",
                                Toast.LENGTH_SHORT).show()

                            val db = FirebaseFirestore.getInstance()
                            db.collection("User").document(email!!)
                                .get()
                                .addOnSuccessListener {
                                    userViewModel.user = it.toObject(User::class.java)
                                    val registrationToken = FirebaseInstanceId.getInstance().token
                                    MyFirebaseInstanceIDService.addTokenToFirestore(registrationToken)
                                }

                                .addOnCompleteListener {
                                    this.findNavController()
                                        .navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                                }
                        }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    binding.btnLogin.isEnabled = true

                    Toast.makeText(
                        activity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun validation(): Boolean {

        if (binding.txtEmail.text.isNullOrBlank() ||
            !Patterns.EMAIL_ADDRESS.matcher(binding.txtEmail.text.toString()).matches() ||
            binding.txtPassword.text.isNullOrBlank()
        ) {

            if (binding.txtEmail.text.isNullOrBlank()) {
                binding.txtEmailLy.error = "*Email is require."
            } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.txtEmail.text.toString()).matches()) {
                binding.txtEmailLy.error = "*Please enter a valid email"
            } else {
                binding.txtEmailLy.isErrorEnabled = false
            }

            if (binding.txtPassword.text.isNullOrBlank()) {
                binding.txtPasswordLy.error = "*Password is require."
            } else {
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

    // take out option menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    // display bottom nav bar, when exit the fragment
    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    fun updateUI(currentUser: FirebaseUser?) {

    }

    private fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken, 0)
    }

}

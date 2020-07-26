package com.example.fyp.stuMaintenanceModule


//import com.google.firebase.auth.FirebaseAuth
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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.fyp.Class.User
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var user: User

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
        if (!validation()) {
            return
        }
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        hideKeyboard()
        assignUser()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    addUserToDatabase()

                    var user = Firebase.auth.currentUser

                    user?.sendEmailVerification()
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
//                                Toast.makeText(
//                                    activity, "Sent verification Email, Please Verify " +
//                                            "Your Email Before Login",
//                                    Toast.LENGTH_SHORT
//                                ).show()
                                val snackbar = Snackbar.make(
                                    root_layout, "Register Successful! Please Verify Your Email" +
                                            "Before Login", Snackbar.LENGTH_LONG
                                )
                                snackbar.setAction("Close", View.OnClickListener {
                                    snackbar.dismiss()
                                })
                                (snackbar.view).layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                                snackbar.show()
                            } else {
                                Toast.makeText(
                                    activity, "Not verification Email" + task.exception,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

//                    val snackbar = Snackbar.make(
//                        root_layout, "Register Successful! Please Verify Your Email" +
//                                "Before Login", Snackbar.LENGTH_LONG
//                    )
//                    snackbar.setAction("Close", View.OnClickListener {
//                        snackbar.dismiss()
//                    })
//                    (snackbar.view).layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
//                    snackbar.show()



                } else {
                    // If sign in fails, display a message to the user.

                    val snackbar = Snackbar.make(
                        root_layout, "Wrong Email or Email Being Registered!", Snackbar.LENGTH_LONG
                    )
                    (snackbar.view).layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    snackbar.show()

                }
            }
    }

    private fun validation(): Boolean {

        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()
        val firstName = binding.txtFirstN.text.toString()
        val lastName = binding.txtLastN.text.toString()
        val phone = binding.txtPhone.text.toString()

        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()
            || !Patterns.EMAIL_ADDRESS.matcher(binding.txtEmail.text.toString()).matches()
            || !isValidPassword(password) ) {

            if (firstName.isEmpty()) {
                binding.txtFirstNLayout.error = "*First name is require."
            }
            else {
                binding.txtFirstNLayout.isErrorEnabled = false
            }

            if (lastName.isEmpty()) {
                binding.txtLastNLayout.error = "*Last name is require."
            }
            else {
                binding.txtLastNLayout.isErrorEnabled = false
            }

            if (phone.isEmpty()) {
                binding.txtPhoneLayout.error = "*Phone number is require."
            }
            else {
                binding.txtPhoneLayout.isErrorEnabled = false
            }

            if (email.isEmpty()) {
                binding.txtEmailLayout.error = "*Email is require."
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(binding.txtEmail.text.toString()).matches()) {
                binding.txtEmailLayout.error = "*Please enter a valid email"
            }
            else {
                binding.txtEmailLayout.isErrorEnabled = false
            }

            if (password.isEmpty()) {
                binding.txtPasswordLayout.error = "*Password is require."
            }
            else if (!isValidPassword(password)) {
                binding.txtPasswordLayout.error = "*Invalid password."
            }
            else {
                binding.txtPasswordLayout.isErrorEnabled = false
            }

            return false

        }

        return true
    }

    private fun isValidPassword(password: String): Boolean {

        val pattern: Pattern
        val matcher: Matcher

        // a digit, a lower case letter must occur at least once & at least six place   s though
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$"

        pattern = Pattern.compile(PASSWORD_PATTERN)
        matcher = pattern.matcher(password)

        return matcher.matches()

    }

    private fun assignUser() {
        user = User("Photo",binding.txtFirstN.text.toString(),binding.txtLastN.text.toString()
        ,binding.txtPhone.text.toString(),"student",binding.txtPassword.text.toString()
            ,binding.txtEmail.text.toString(), mutableListOf())
    }

    private fun addUserToDatabase() {
        val db = FirebaseFirestore.getInstance()

        db.collection("User").document(user.email!!)
            .set(user)
            .addOnSuccessListener {
                Log.i("upload", "success")
            }
            .addOnFailureListener {
                Log.i("upload", "Error adding document", it)
            }
            .addOnCompleteListener {
                Toast.makeText(activity, "Successfully Added", Toast.LENGTH_SHORT).show();
            }
    }


    //clear option menu
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(view?.windowToken,0)
    }

}



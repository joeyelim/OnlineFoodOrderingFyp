package com.example.fyp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.fyp.LoginModule.LoginFragment
import com.example.fyp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        containedBtnSignUp.setOnClickListener {
            signUpUser()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    }

    private fun signUpUser() {
        if (binding.txtEmail.editText?.text.toString().isEmpty()) {
            txtEmail.error = "Please enter email"
            txtEmail.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.txtEmail.editText?.text.toString()).matches()) {
            txtEmail.error = "Please enter a valid email"
            txtEmail.requestFocus()
            return
        }

        if (binding.txtPassword.editText?.text.toString().isEmpty()) {
            txtPassword.error = "Please enter password"
            txtPassword.requestFocus()
            return
        }

//        auth.createUserWithEmailAndPassword(txtEmail.text.toString(), txtPassword.text.toString())
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    startActivity(Intent(this, LoginFragment::class.java))
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(
//                        baseContext, "Authentication failed. Try again after some time.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                }
//
//            }
    }
}

package com.example.fyp.LoginModule


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fyp.R
import com.example.fyp.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

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

        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_login, container, false
        )
//        btnSignUp.setOnClickListener {
//            it.findNavController().navigate(com.example.fyp.R.id.action_login_to_registration)
//        }
        setHasOptionsMenu(false)

        binding.btnSignUp.setOnClickListener {
            it.findNavController()
                .navigate(R.id.action_login_to_registration)
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun updateUI(currentUser: FirebaseUser?) {

    }

}

package com.example.fyp.LoginModule


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.databinding.FragmentForgotPasswordBinding

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
            it.findNavController()
                .navigate(ForgotPasswordFragmentDirections.actionFragmentForgotPasswordToResetPwdFragment())
        }

        binding.btnBack.setOnClickListener {
            it.findNavController()
                .navigate(ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToFragmentLogin())

        }

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}

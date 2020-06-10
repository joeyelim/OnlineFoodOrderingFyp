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
import com.example.fyp.databinding.FragmentResetPwdBinding

/**
 * A simple [Fragment] subclass.
 */
class ResetPwdFragment : Fragment() {
    private lateinit var binding: FragmentResetPwdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_reset_pwd, container, false
        )
        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.btnBack.setOnClickListener{
            it.findNavController()
                .navigate(ResetPwdFragmentDirections.actionResetPwdFragmentToFragmentForgotPassword())
        }

        binding.btnConfirm.setOnClickListener {
            it.findNavController()
                .navigate(ResetPwdFragmentDirections.actionResetPwdFragmentToResetSucessfulFragment())
        }
        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }


}

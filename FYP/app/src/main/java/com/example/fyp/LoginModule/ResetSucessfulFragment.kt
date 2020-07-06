package com.example.fyp.LoginModule


import android.graphics.Paint
import android.os.Bundle
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
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentResetSucessfulBinding

/**
 * A simple [Fragment] subclass.
 */
class ResetSucessfulFragment : Fragment() {

    private lateinit var binding: FragmentResetSucessfulBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= DataBindingUtil.inflate(
            inflater, R.layout.fragment_reset_sucessful, container, false
        )

        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        intiUI()

        binding.btnLogin.paintFlags = binding.btnLogin.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.btnLogin.setOnClickListener {
            it.findNavController()
                .navigate(ResetSucessfulFragmentDirections.actionResetSucessfulFragmentToFragmentLogin())
        }

        return binding.root
    }

    private fun intiUI() {
        binding.txtUserName.setText(userViewModel.user?.first_name + " " + userViewModel.user?.last_name)
        binding.txtUserEmail.setText(userViewModel.user?.email)

    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}

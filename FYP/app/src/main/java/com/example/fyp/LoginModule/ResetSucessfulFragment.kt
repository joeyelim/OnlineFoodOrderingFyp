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
import com.example.fyp.databinding.FragmentResetSucessfulBinding

/**
 * A simple [Fragment] subclass.
 */
class ResetSucessfulFragment : Fragment() {

    private lateinit var binding: FragmentResetSucessfulBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(
            inflater, R.layout.fragment_reset_sucessful, container, false
        )
        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.btnLogin.setOnClickListener {
            it.findNavController()
                .navigate(ResetSucessfulFragmentDirections.actionResetSucessfulFragmentToFragmentLogin())
        }

        return binding.root
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}

package com.example.fyp.stuMaintenanceModule


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.databinding.FragmentChangePwdBinding


/**
 * A simple [Fragment] subclass.
 */
class ChangePwdFragment : Fragment() {
    private lateinit var binding: FragmentChangePwdBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_change_pwd, container, false
        )

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}

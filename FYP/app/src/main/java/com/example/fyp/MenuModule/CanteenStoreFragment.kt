package com.example.fyp.MenuModule


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.databinding.FragmentCanteenStoreBinding

/**
 * A simple [Fragment] subclass.
 */
class CanteenStoreFragment : Fragment() {

    private lateinit var binding: FragmentCanteenStoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_home, container, false
        )

        (activity as MainActivity).setNavVisible()

        return binding.root
    }



}

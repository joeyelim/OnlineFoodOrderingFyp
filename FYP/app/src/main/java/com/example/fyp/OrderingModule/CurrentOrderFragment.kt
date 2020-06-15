package com.example.fyp.OrderingModule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fyp.MainActivity
import com.example.fyp.databinding.FragmentCurrentOrderBinding

class CurrentOrderFragment : Fragment() {

    private lateinit var binding: FragmentCurrentOrderBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_current_order, container, false
        )

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()


        return binding.root
    }

}
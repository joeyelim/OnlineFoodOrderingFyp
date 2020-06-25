package com.example.fyp.OrderingModule


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
import com.example.fyp.databinding.FragmentPlaceOrderProgress2Binding

/**
 * A simple [Fragment] subclass.
 */
class PlaceOrderProgress2Fragment : Fragment() {
    private lateinit var binding: FragmentPlaceOrderProgress2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_place_order_progress2, container, false
        )

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.btnConfirm.setOnClickListener{
            it.findNavController()
                .navigate(PlaceOrderProgress2FragmentDirections
                    .actionPlaceOrderProgress2FragmentToPlaceOrderProgress3Fragment())
        }

        binding.btnBack.setOnClickListener{
            it.findNavController()
                .navigate(PlaceOrderProgress2FragmentDirections
                    .actionPlaceOrderProgress2FragmentToPlaceOrderFragment())
        }


        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

}

package com.example.fyp.OrderingModule


import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CartViewModel
import com.example.fyp.databinding.FragmentPlaceOrderBinding
import kotlinx.android.synthetic.main.fragment_place_order.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class PlaceOrderFragment : Fragment() {
    private lateinit var binding: FragmentPlaceOrderBinding
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_place_order, container, false
        )

        cartViewModel = ViewModelProviders.of(activity!!).get(CartViewModel::class.java)

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.btnNext.setOnClickListener{
            cartViewModel.setOrderValue("Time", "Option")

            it.findNavController()
                .navigate(PlaceOrderFragmentDirections.actionPlaceOrderFragmentToPlaceOrderProgress2Fragment())
        }

        binding.btnBack.setOnClickListener{
            it.findNavController()
                .navigate(PlaceOrderFragmentDirections.actionPlaceOrderFragmentToCartFragment())
        }

        binding.btnTime.setOnClickListener{
            timePicker()
        }

        return binding.root
    }

    private fun timePicker(){
        val cal:Calendar = Calendar.getInstance()
        val timeSetListener:TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                textViewTime.text = SimpleDateFormat("HH:mm").format(cal.time)
                //textViewTime.text = cal.time.toString()
            }

        TimePickerDialog(activity, R.style.DialogTheme, timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }


}

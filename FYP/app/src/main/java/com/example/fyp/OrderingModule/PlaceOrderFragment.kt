package com.example.fyp.OrderingModule


import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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

        binding.btnNext.setOnClickListener {
            cartViewModel.setOrderValue("Time", "Option")

            if (!placeOrderValidation()) {

            } else
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

    private fun placeOrderValidation():Boolean {
        Log.i("456","456")
        val selectedtime = binding.textViewTime.text.toString()

        val timeStart = "08:00"
        val time1 = SimpleDateFormat("HH:mm").parse(timeStart)
        val calendarStart = Calendar.getInstance()
        calendarStart.time = time1
        calendarStart.add(Calendar.DATE, 1)

        val timeEnd = "20:00"
        val time2 = SimpleDateFormat("HH:mm").parse(timeEnd)
        val calendarEnd = Calendar.getInstance()
        calendarEnd.time = time2
        calendarEnd.add(Calendar.DATE, 1)

            if (selectedtime == "Pick up time")
            {
                Log.i("123","123")
                binding.errorMsg.setText("Please select your pick up time.")
                binding.errorMsg.visibility = View.VISIBLE
                return false
            }
            else if (selectedtime != "Pick up time")
            {
                val timeSelected = selectedtime
                val time3 = SimpleDateFormat("HH:mm").parse(timeSelected)
                val calendarSelect = Calendar.getInstance()
                calendarSelect.time = time3
                calendarSelect.add(Calendar.DATE, 1)

                val x = calendarSelect.getTime()
                if(x.before(calendarStart.getTime())||x.after(calendarEnd.getTime()))
                {
                    Log.i("323","323")
                    binding.errorMsg.setText("Please select the time within the operating hours (8:00 AM to 20:00 PM).")
                    binding.errorMsg.visibility = View.VISIBLE
                    return false
                }
                else
                {
//                    binding.errorMsg.visibility = View.GONE
                    return true
                }
            }
            else
                return false
    }

    private fun timePicker(){
        val cal:Calendar = Calendar.getInstance()
        val timeSetListener:TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                textViewTime.text = SimpleDateFormat("HH:mm aa").format(cal.time)

            }

        TimePickerDialog(activity, com.example.fyp.R.style.DialogTheme, timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }


}

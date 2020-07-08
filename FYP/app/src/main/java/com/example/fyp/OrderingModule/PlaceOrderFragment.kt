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

        binding.btnNext.setOnClickListener{
            cartViewModel.option = getOption()

            it.findNavController()
                .navigate(PlaceOrderFragmentDirections.actionPlaceOrderFragmentToPlaceOrderProgress2Fragment())

//            if (!placeOrderValidation()) {
//
//            }else
//                it.findNavController()
//                    .navigate(PlaceOrderFragmentDirections.actionPlaceOrderFragmentToPlaceOrderProgress2Fragment())
        }

        binding.btnBack.setOnClickListener{
            cartViewModel.removeAll()
            it.findNavController()
                .navigate(PlaceOrderFragmentDirections.actionPlaceOrderFragmentToCartFragment())
        }

        binding.btnTime.setOnClickListener{
            timePicker()
        }

        return binding.root
    }

    private fun placeOrderValidation():Boolean {
        val selectedtime = binding.textViewTime.text.toString()
        // store operating start time into calendarStart
        val timeStart = "08:00"
        val time1 = SimpleDateFormat("HH:mm").parse(timeStart)
        val calendarStart = Calendar.getInstance()
        calendarStart.time = time1
        calendarStart.add(Calendar.DATE, 1)

        // store operating end time into calendarEnd
        val timeEnd = "20:00"
        val time2 = SimpleDateFormat("HH:mm").parse(timeEnd)
        val calendarEnd = Calendar.getInstance()
        calendarEnd.time = time2
        calendarEnd.add(Calendar.DATE, 1)

        if (selectedtime == "Pick up time")
        {
            binding.errorMsg.setText(R.string.error_empty_time)
            binding.errorMsg.visibility = View.VISIBLE
            return false
        }
        else if (selectedtime != "Pick up time")
        {
            // store user selected time into calendarSelect
            val timeSe = selectedtime
            val time3 = SimpleDateFormat("HH:mm").parse(timeSe)
            val calendarSelect = Calendar.getInstance()
            calendarSelect.time = time3
            calendarSelect.add(Calendar.DATE, 1)
            val x = calendarSelect.getTime()

            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("HH:mm aa")
            val formatedDate = formatter.format(date)

            if (x.before(calendarStart.getTime()) || x.after(calendarEnd.getTime()))
            {
                binding.errorMsg.setText(R.string.error_pick_time)
                binding.errorMsg.visibility = View.VISIBLE
                return false
            }
            else
            {
                return if (x.before(date)) {
                    binding.errorMsg.setText("Please selected the time after current time($formatedDate)!")
                    binding.errorMsg.visibility = View.VISIBLE
                    false
                } else {
                    true
                }
            }
        }
        else
            return false
    }

    private fun getPickTime() : String {
        return binding.txtPickupTime.text.toString()
    }

    private fun getOption() : String {
        if (binding.radioButton1.isChecked) {
            return binding.radioButton1.text.toString()
        } else {
            return return binding.radioButton2.text.toString()
        }
    }

    private fun timePicker(){
        val cal:Calendar = Calendar.getInstance()
        val timeSetListener:TimePickerDialog.OnTimeSetListener =
            TimePickerDialog.OnTimeSetListener{timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                textViewTime.text = SimpleDateFormat("HH:mm aa").format(cal.time)
                cartViewModel.setTime(SimpleDateFormat("HH:mm").format(cal.time))
                //textViewTime.text = cal.time.toString()
            }

        TimePickerDialog(activity, com.example.fyp.R.style.DialogTheme, timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }


}

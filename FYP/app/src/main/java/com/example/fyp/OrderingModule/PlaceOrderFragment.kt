package com.example.fyp.OrderingModule


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.fyp.R

/**
 * A simple [Fragment] subclass.
 */
class PlaceOrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fun onRadioButtonClicked(view: View) {
            if (view is RadioButton) {
                // Is the button now checked?
                val checked = view.isChecked

                // Check which radio button was clicked
                when (view.getId()) {
                    R.id.radio_dine_in ->
                        if (checked) {
                            // dine in are the best
                        }
                    R.id.radio_take_away ->
                        if (checked) {
                            // take away rule
                        }
                }
            }
        }


        return inflater.inflate(R.layout.fragment_place_order, container, false)
    }


}

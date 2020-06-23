package com.example.fyp.MenuModule


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.databinding.FragmentFoodDetailBinding

/**
 * A simple [Fragment] subclass.
 */
class FoodDetailFragment : Fragment() {
    private lateinit var binding: FragmentFoodDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_food_detail, container, false
        )

//        binding.imgCart.setOnClickListener(){
//            it.findNavController().navigate()
//        }

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

        binding.imgCart.setOnClickListener{
            it.requestFocus()
            it.findNavController()
                .navigate(FoodDetailFragmentDirections.actionFoodDetailFragmentToCartFragment())
        }

        binding.imgStar.setOnClickListener{
            val dialog = AlertDialog.Builder(activity)
            val dialogView = layoutInflater.inflate(R.layout.fragment_rating, null)

            dialog.setView(dialogView)
            dialog.setCancelable(true)
            dialog.show()
        }

        return binding.root
    }

    fun dialog(){
        val dialog = AlertDialog.Builder(activity)
        val dialogView = layoutInflater.inflate(R.layout.fragment_rating, null)

        dialog.setView(dialogView)
        dialog.setCancelable(false)
        dialog.show()

//        val customDialog = dialog.create()
//        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener({
//
//        })

    }
}

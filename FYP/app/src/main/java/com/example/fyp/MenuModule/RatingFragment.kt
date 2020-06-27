package com.example.fyp.MenuModule



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.fyp.databinding.FragmentRatingBinding
import android.graphics.PorterDuff
import android.graphics.drawable.LayerDrawable
import android.R
import android.graphics.Color
import android.graphics.Paint
import android.widget.RatingBar
import androidx.lifecycle.ViewModelProviders
import com.example.fyp.ViewModel.CanteenViewModel
import com.google.firebase.storage.FirebaseStorage


/**
 * A simple [Fragment] subclass.
 */
class RatingFragment : Fragment() {
    private lateinit var binding: FragmentRatingBinding
    private lateinit var viewModel: CanteenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_rating, container, false
        )

        viewModel = ViewModelProviders.of(activity!!).get(CanteenViewModel::class.java)
        intiUI()

        return binding.root
    }

    private fun intiUI() {
        binding.txtFoodName.text = viewModel.food.food_name
        binding.txtFoodName.setPaintFlags( binding.txtFoodName.paintFlags or Paint.UNDERLINE_TEXT_FLAG)


        val image = binding.imgFood
        val a = FirebaseStorage.getInstance().getReference(viewModel.food.food_image!!)
        viewModel.setImage(image, a)
    }

}

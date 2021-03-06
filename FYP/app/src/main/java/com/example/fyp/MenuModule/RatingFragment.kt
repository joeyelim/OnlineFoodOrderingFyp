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
import android.util.Log
import android.widget.RatingBar
import androidx.lifecycle.Observer
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

        return binding.root
    }

}

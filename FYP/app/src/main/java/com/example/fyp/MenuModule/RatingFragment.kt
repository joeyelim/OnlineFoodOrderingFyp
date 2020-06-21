package com.example.fyp.MenuModule


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.fyp.R
import com.example.fyp.databinding.FragmentRatingBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass.
 */
class RatingFragment : Fragment() {
    private lateinit var binding: FragmentRatingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_rating, container, false
        )

        val dialog = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.fragment_rating, null)

        return binding.root
    }


}

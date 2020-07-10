package com.example.fyp.CanteenStaffNotif


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.fyp.R
import com.example.fyp.databinding.FragmentStaffNotificationBinding

/**
 * A simple [Fragment] subclass.
 */
class StaffNotificationFragment : Fragment() {
    private lateinit var binding: FragmentStaffNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_staff_notification, container, false
        )

        binding.fabBtn.setOnClickListener{
            it.findNavController()
                .navigate(StaffNotificationFragmentDirections.actionStaffNotificationFragmentToStaffCreatePostFragment())
        }
        return binding.root
    }


}

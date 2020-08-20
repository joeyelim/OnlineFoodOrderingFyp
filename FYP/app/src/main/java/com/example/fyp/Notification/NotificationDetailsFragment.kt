//package com.example.fyp.Notification
//
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.Menu
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.lifecycle.ViewModelProviders
//import androidx.navigation.findNavController
//import com.example.fyp.MainActivity
//import com.example.fyp.R
//import com.example.fyp.ViewModel.UserViewModel
//
//import com.example.fyp.databinding.FragmentNotificationDetailsBinding
//
///**
// * A simple [Fragment] subclass.
// */
//class NotificationDetailsFragment : Fragment() {
//    private lateinit var binding: FragmentNotificationDetailsBinding
//    private lateinit var viewModel : UserViewModel
//
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(
//            inflater, R.layout.fragment_notification_details, container, false
//        )
//
//        setHasOptionsMenu(true)
//        (activity as MainActivity).setNavInvisible()
//        viewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
//
//
//        intiUI()
//
//        return binding.root
//    }
//
//    private fun intiUI() {
//
//        binding.txtNotifTitle.text = viewModel.notification.title
//
//        binding.txtStaffName.text = viewModel.notification.sender
//        binding.txtDate.text = viewModel.notification.date
//        binding.txtNotifContent.text = viewModel.notification.content
//
//
//    }
//
//    override fun onPrepareOptionsMenu(menu: Menu) {
//        menu.clear()
//    }
//}

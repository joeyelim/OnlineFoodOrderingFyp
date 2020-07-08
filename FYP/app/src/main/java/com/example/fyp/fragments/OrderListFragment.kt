package com.example.fyp.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.fyp.CurrentOrder2
import com.example.fyp.MainActivity
import com.example.fyp.OrderingModule.OrderHistoryFragment
import com.example.fyp.databinding.FragmentOrderListBinding
import kotlinx.android.synthetic.main.fragment_order_list.*
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.fyp.R
import com.example.fyp.ViewModel.UserViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


/**
 * A simple [Fragment] subclass.
 */
class OrderListFragment : Fragment() {

    private lateinit var binding: FragmentOrderListBinding
    private lateinit var userViewModel : UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_order_list, container, false
        )

        (activity as MainActivity).setNavVisible()
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        checkLogin()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()


        /* -------- outer tab layout ------------------*/
        val outTab = binding.tabLayout
        val outterViewPager = OutterViePagerAdapter(childFragmentManager)
        val viewPager = binding.orderListViewPager

        viewPager.adapter = outterViewPager

        // to disable swipe
        // source https://stackoverflow.com/questions/9650265/how-do-disable-paging-by-swiping-with-finger-in-viewpager-but-still-be-able-to-s/13392198#13392198
        viewPager?.setOnTouchListener { v, _ ->
            for (PAGE in 0..viewPager.adapter!!.count){
                if (viewPager.currentItem == PAGE){
                    viewPager.setCurrentItem(PAGE-1,false)
                    viewPager.setCurrentItem(PAGE,false)
                }}
            true
        }

        outTab.setupWithViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)

    }

    private fun checkLogin() {
        if (Firebase.auth.currentUser == null || userViewModel.user?.email == "") {
            findNavController().navigate(R.id.action_orderListFragment_to_fragment_home)
        }
    }

     class OutterViePagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {
        override fun getItem(position: Int): Fragment {

            return if (position == 0) {
                CurrentOrder2()
            } else {
                OrderHistoryFragment()
            }

        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return if (position == 0) {
                "Current Order"
            } else {
                "Order History"
            }
        }

    }

}

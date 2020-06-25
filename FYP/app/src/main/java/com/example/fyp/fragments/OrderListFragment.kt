package com.example.fyp.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.fyp.CurrentOrder2
import com.example.fyp.MainActivity
import com.example.fyp.OrderingModule.CurrentOrderFragment
import com.example.fyp.OrderingModule.OrderHistoryFragment
import com.example.fyp.databinding.FragmentOrderListBinding
import kotlinx.android.synthetic.main.fragment_order_list.*
import android.view.MotionEvent
import android.text.method.Touch.onTouchEvent




/**
 * A simple [Fragment] subclass.
 */
class OrderListFragment : Fragment() {

    private lateinit var binding: FragmentOrderListBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_order_list, container, false
        )

        (activity as MainActivity).setNavVisible()

        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onResume() {
        super.onResume()

        val supportFragmentManager = this.activity!!.supportFragmentManager

        /* -------- outer tab layout ------------------*/
        val outTab = binding.tabLayout
        val outterViewPager = OutterViePagerAdapter(supportFragmentManager)
        val viewPager = binding.orderListViewPager

        viewPager.adapter = outterViewPager

        // to disable swipe
        // source https://stackoverflow.com/questions/9650265/how-do-disable-paging-by-swiping-with-finger-in-viewpager-but-still-be-able-to-s/13392198#13392198
        viewPager?.setOnTouchListener { v, event ->
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

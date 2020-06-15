package com.example.fyp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.fyp.MainActivity
import com.example.fyp.MyAdapter

import com.example.fyp.R
import com.example.fyp.databinding.FragmentOrderListBinding
import com.google.android.material.tabs.TabLayout

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



        tabLayout()


        return binding.root
    }

    internal class ViewPagerAdapter(fragmentManager: FragmentManager):
        FragmentPagerAdapter(fragmentManager){

        private val fragments: ArrayList<Fragment>
        private val titles: ArrayList<String>

        init {
            fagments= ArrayList()
        }

        override fun getItem(position: Int): Fragment {

        }

        override fun getCount(): Int {

        }

    }

    fun tabLayout(){
        binding.tabLayout!!.addTab(binding.tabLayout!!.newTab().setText("Current Order"))
        binding.tabLayout!!.addTab(binding.tabLayout!!.newTab().setText("Order History"))

        binding.tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        binding.viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        binding.tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager!!.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }



}

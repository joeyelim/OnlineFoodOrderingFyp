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
import com.example.fyp.OrderingModule.CurrentOrderFragment

import com.example.fyp.R
import com.example.fyp.databinding.FragmentOrderListBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_order_list.*

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


        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(CurrentOrderFragment(), "Current Order")
        viewPagerAdapter.addFragment(CurrentOrderFragment(), "Order History")

        viewPagerAdapter.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(viewPager)


        return binding.root
    }

    internal class ViewPagerAdapter(fragmentManager: FragmentManager):
        FragmentPagerAdapter(fragmentManager){

        private val fragments: ArrayList<Fragment>
        private val titles: ArrayList<String>

        init {
            fragments= ArrayList<Fragment>()
            titles= ArrayList<String>()
        }

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment:Fragment, title:String){
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(i: Int):CharSequence?{
            return titles[i]
        }

    }





}

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
import com.example.fyp.OrderingModule.OrderHistoryFragment
import com.example.fyp.R
import com.example.fyp.databinding.FragmentOrderListBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_order_list.*
import androidx.fragment.app.FragmentActivity
import android.app.Activity
import android.content.Context


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

        val supportFragmentManager = this.activity!!.supportFragmentManager


        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.addFragment(CurrentOrderFragment(), "Current Order")
        viewPagerAdapter.addFragment(CurrentOrderFragment(), "Order History")

        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)



        return binding.root
    }

    internal class ViewPagerAdapter(fragmentManager: FragmentManager):
        FragmentPagerAdapter(fragmentManager){

        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()

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

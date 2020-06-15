package com.example.fyp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.fyp.MainActivity
import com.example.fyp.OrderingModule.CurrentOrderFragment
import com.example.fyp.databinding.FragmentOrderListBinding


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

        viewPagerAdapter.setFragment()

        val viewPager = binding.viewPager
        val tabLayout = binding.tabProgress

        viewPager.adapter = viewPagerAdapter
        binding.tabProgress.setupWithViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)


        return binding.root
    }

    // can pass in arrayList of String for maintainability
    internal class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {

        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            //  parameter can use arrayList, probably will
            // just use an identifier
            return CurrentOrderFragment().newInstance(position.toString())
        }

        override fun getCount(): Int {
            return 4
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }


        fun setFragment() {
            titles.add("Pending")
            titles.add("Preparing")
            titles.add("Completed")
            titles.add("Taken")
        }

        override fun getPageTitle(i: Int): CharSequence? {
            return titles[i]
        }

    }







}

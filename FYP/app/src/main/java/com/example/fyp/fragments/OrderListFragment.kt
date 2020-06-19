package com.example.fyp.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.fyp.MainActivity
import com.example.fyp.OrderingModule.CurrentOrderFragment
import com.example.fyp.OrderingModule.OrderHistoryFragment
import com.example.fyp.R
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
            inflater, R.layout.fragment_order_list, container, false
        )

        (activity as MainActivity).setNavVisible()



        return binding.root
    }

    override fun onResume() {
        super.onResume()


        /* --------this is for viewpage------------------*/

        val supportFragmentManager = this.activity!!.supportFragmentManager

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewPagerAdapter.setFragment()

        val viewPager = binding.viewPager
        val tabLayout = binding.tabProgress

        viewPager.adapter = viewPagerAdapter
        binding.tabProgress.setupWithViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)?.setIcon(R.drawable.ic_one)
        tabLayout.getTabAt(1)?.setIcon(R.drawable.ic_one)
        tabLayout.getTabAt(2)?.setIcon(R.drawable.ic_one)
    }

    /* --------this is for viewpage------------------*/

    // can pass in arrayList of String for maintainability
    internal class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {

        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            //  parameter can use arrayList, probably will
            // just use an identifier
            Log.i("test", position.toString())
            Log.i("test", titles[position])
            return CurrentOrderFragment().newInstance(titles[position])
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
            titles.add("Ready")
            titles.add("Taken")
        }

        override fun getPageTitle(i: Int): CharSequence? {
            return titles[i]
        }

    }



}

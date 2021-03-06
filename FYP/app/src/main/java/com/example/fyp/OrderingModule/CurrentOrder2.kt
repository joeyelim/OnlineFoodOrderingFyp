package com.example.fyp.OrderingModule


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.fyp.R
import com.example.fyp.databinding.FragmentCurrentOrder2Binding

/**
 * A simple [Fragment] subclass.
 */
class CurrentOrder2 : Fragment() {
    private lateinit var binding: FragmentCurrentOrder2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_current__order_2, container, false
        )

        initUI()

        return binding.root
    }

    private fun initUI() {
        val supportFragmentManager = this.activity!!.supportFragmentManager

        val viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager)

//        viewPagerAdapter.setFragment()
        viewPagerAdapter.addFragment(PendingOrder(),"Pending")
        viewPagerAdapter.addFragment(PreparingOrder(),"Preparing")
        viewPagerAdapter.addFragment(ReadyOrder(),"Ready")

        val viewPager = binding.viewPager
        val tabLayout = binding.tabProgress

        viewPager.adapter = viewPagerAdapter
        binding.tabProgress.setupWithViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)

        val tabIcons = intArrayOf(
            R.drawable.ic_circled_1,
            R.drawable.ic_circled_2,
            R.drawable.ic_circled_3
        )

        tabLayout.getTabAt(0)?.setIcon(tabIcons[0])
        tabLayout.getTabAt(1)?.setIcon(tabIcons[1])
        tabLayout.getTabAt(2)?.setIcon(tabIcons[2])
    }

    internal class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

        private val fragments: ArrayList<Fragment> = ArrayList()
        private val titles: ArrayList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            //  parameter can use arrayList, probably will
            // just use an identifier
//            return CurrentOrderFragment().newInstance(titles[position])
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }

        override fun getPageTitle(i: Int): CharSequence? {
            return titles[i]
        }

    }



}

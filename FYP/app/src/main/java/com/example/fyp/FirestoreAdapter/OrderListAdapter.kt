package com.example.fyp.FirestoreAdapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.fyp.OrderingModule.CurrentOrderFragment
import com.example.fyp.OrderingModule.OrderHistoryFragment

class OrderListAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return CurrentOrderFragment()
            }
            1 -> {
                return OrderHistoryFragment()
            }
            else -> return CurrentOrderFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}
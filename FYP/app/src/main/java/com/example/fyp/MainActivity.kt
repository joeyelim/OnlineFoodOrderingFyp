package com.example.fyp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.fyp.fragments.CartFragment
import com.example.fyp.fragments.HomeFragment
import com.example.fyp.fragments.NotificationFragment
import com.example.fyp.fragments.OrderListFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.fyp.LoginModule.LoginFragment
import com.example.fyp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_login.*



class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = this.findNavController(R.id.myNavHostFragment)
        bottomNavigationView.setupWithNavController(navController)

        // Pass the IDs of top-level destinations in AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                R.id.fragment_home,
                R.id.cartFragment,
                R.id.orderListFragment,
                R.id.profileFragment
            )

        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setItemBackgroundResource(R.drawable.menu_background);

//        setSupportActionBar(toolbar)

//        val nacController = Navigation.findNavController(this, R.id.fragmentContainer)
//
//        setupBottomNavMenu(nacController)
//        setupActionBar(nacController)

        //load first fragment by default
//        loadFragment(HomeFragment())

//        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
//            when{
//                menuItem.itemId == R.id.ic_home -> {
//                    loadFragment(HomeFragment())
//                    return@setOnNavigationItemSelectedListener true
//                }
//                menuItem.itemId == R.id.ic_cart -> {
//                    loadFragment(CartFragment())
//                    return@setOnNavigationItemSelectedListener true
//                }
//                menuItem.itemId == R.id.ic_orderList -> {
//                    loadFragment(OrderListFragment())
//                    return@setOnNavigationItemSelectedListener true
//                }
//                menuItem.itemId == R.id.ic_notification -> {
//                    loadFragment(NotificationFragment())
//                    return@setOnNavigationItemSelectedListener true
//                }
//                menuItem.itemId == R.id.ic_profile -> {
//                    loadFragment(LoginFragment())
//                    return@setOnNavigationItemSelectedListener true
//                }
//                else -> {
//                    return@setOnNavigationItemSelectedListener false
//                }
//            }
//        }


    }

//    private fun setupBottomNavMenu(navController: NavController){
//        bottomNavigationView?.let {
//            NavigationUI.setupWithNavController(it,navController)
//        }
//    }
//
//    private fun setupActionBar(navController: NavController){
//        NavigationUI.setupActionBarWithNavController(this, navController)
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.top_option_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navController = Navigation.findNavController(this,R.id.fragmentContainer)
//        val navigated = NavigationUI.onNavDestinationSelected(item!!, navController)
        when(item.itemId) {
            R.id.profileFragment -> {
                Toast.makeText(this, "You haven't login...",Toast.LENGTH_LONG).show()
                item.onNavDestinationSelected(navController)
                setNavInvisible()
                return true
            }
            R.id.fragment_login -> {
                item.onNavDestinationSelected(navController)
                setNavInvisible()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

//    override fun onSupportNavigationUp():Boolean {
//        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.fragmentContainer))
//    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



    fun setNavInvisible(){
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun setNavVisible(){
        binding.bottomNavigationView.visibility = View.VISIBLE
    }



//    private fun loadFragment(fragment:Fragment){
//        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
//            fragmentTransaction.replace(R.id.fragmentContainer,fragment)
//            fragmentTransaction.commit()
//        }
//
//    }

}

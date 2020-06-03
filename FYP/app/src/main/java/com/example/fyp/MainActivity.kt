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
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.fyp.LoginModule.LoginFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_login.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setSupportActionBar(toolbar)

//        val nacController = Navigation.findNavController(this, R.id.fragmentContainer)
//
//        setupBottomNavMenu(nacController)
//        setupActionBar(nacController)

        //load first fragment by default
        loadFragment(HomeFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when{
                menuItem.itemId == R.id.ic_home -> {
                    loadFragment(HomeFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId == R.id.ic_cart -> {
                    loadFragment(CartFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId == R.id.ic_orderList -> {
                    loadFragment(OrderListFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId == R.id.ic_notification -> {
                    loadFragment(NotificationFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                menuItem.itemId == R.id.ic_profile -> {
                    loadFragment(LoginFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }

        bottomNavigationView.setItemBackgroundResource(R.drawable.menu_background);
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
            R.id.ic_profile -> {
                Toast.makeText(this, "You haven't login...",Toast.LENGTH_LONG).show()
                loadFragment(LoginFragment())
                setNavInvisible()
                return true
            }
            R.id.ic_login -> {
                loadFragment(LoginFragment())
                setNavInvisible()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun onSupportNavigationUp():Boolean {
//        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.fragmentContainer))
//    }



    private fun setNavInvisible(){
        var bottomNav: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNav.visibility = View.GONE
    }

//    private fun setNavVisible(){
//        var bottomNav: BottomNavigationView = findViewById(R.id.bottomNavigationView)
//        bottomNav.visibility = View.VISIBLE
//    }

    private fun loadFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction.replace(R.id.fragmentContainer,fragment)
            fragmentTransaction.commit()
        }

    }

}

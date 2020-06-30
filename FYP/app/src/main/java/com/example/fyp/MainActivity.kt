package com.example.fyp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.*
import com.example.fyp.Class.User
import com.example.fyp.LoginModule.LoginFragment
import com.example.fyp.LoginModule.LoginFragmentDirections
import com.example.fyp.ViewModel.LoginViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = this.findNavController(R.id.myNavHostFragment)
        bottomNavigationView.setupWithNavController(navController)

        // Pass the IDs of top-level destinations in AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.fragment_home,
                R.id.cartFragment,
                R.id.orderListFragment,
                R.id.profileFragment
            )

        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setItemBackgroundResource(R.drawable.menu_background)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

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
    override fun onResume() {
        super.onResume()
        observeAuthenticationState()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.top_option_menu, menu)


        loginViewModel.changeMenuOption.observe(this, Observer {
            if (it) {
                menu?.findItem(R.id.fragment_login)?.title = "Logout"
            } else {
                menu?.findItem(R.id.fragment_login)?.title = "Login"
            }
        })

        observeChangeOption(menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navController = Navigation.findNavController(this,R.id.fragmentContainer)
//        val navigated = NavigationUI.onNavDestinationSelected(item!!, navController)
        when (item.itemId) {
            R.id.profileFragment -> {
                if (Firebase.auth.currentUser != null) {
                    item.onNavDestinationSelected(navController)
                    setNavInvisible()
                } else {
                    Toast.makeText(this, "You haven't login...", Toast.LENGTH_LONG).show()
                    navController.navigate(R.id.fragment_login)
                }
                return true
            }
            R.id.fragment_login -> {
                if (Firebase.auth.currentUser != null) {
                    loginViewModel.changeOption(false)
                    userViewModel.user = User()
                    Firebase.auth.signOut()
                } else {
                    item.onNavDestinationSelected(navController)
                    setNavInvisible()
                }
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

    private fun observeChangeOption(menu : Menu?) {
//        loginViewModel.changeMenuOption?.observe(this, Observer {
//            if (it) {
//                menu.findItem(R.id.fragment_login).title = "Logout"
//            } else {
//                menu.findItem(R.id.fragment_login).title = "Login"
//            }
//        })
    }

    public fun changeTitle() {

    }

    private fun observeAuthenticationState() {
        loginViewModel.authenticationState?.observe(this, Observer { authenticationState ->
            if (authenticationState == LoginViewModel.AuthenticationState.AUTHENTICATED) {
                loginViewModel.setCurrentUser()
            }
        })
    }


    fun setNavInvisible() {
        binding.bottomNavigationView.visibility = View.GONE
    }

    fun setNavVisible() {
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

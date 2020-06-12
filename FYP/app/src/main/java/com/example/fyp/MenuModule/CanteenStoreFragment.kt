package com.example.fyp.MenuModule


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Class.CanteenStore
import com.example.fyp.FirestoreAdapter.CanteenFireStoreRecyclerAdapter
import com.example.fyp.FirestoreAdapter.StoreFirestoreAdapter
import com.example.fyp.FirestoreAdapter.onListClick
import com.example.fyp.FirestoreAdapter.onListClick1
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.databinding.FragmentCanteenStoreBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

/**
 * A simple [Fragment] subclass.
 */
class CanteenStoreFragment : Fragment(), onListClick1 {

    private lateinit var binding: FragmentCanteenStoreBinding
    private var adapter: StoreFirestoreAdapter? = null
//    private lateinit var navController: NavController
//    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_canteen_store, container, false
        )

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavInvisible()

//        appBarConfiguration = AppBarConfiguration(
//            topLevelDestinationIds = setOf (
//                R.id.cartFragment
//            )
//
//        )

        initRecycleView()


        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//
//        val inflater = menuInflater
//        inflater.inflate(R.menu.food_app_bar, menu)
//
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.profileFragment -> {
//                Toast.makeText(this, "You haven't login...", Toast.LENGTH_LONG).show()
//                item.onNavDestinationSelected(navController)
//                return true
//            }else -> {
//                return super.onOptionsItemSelected(item)
//            }
//        }
//    }
//

    fun initRecycleView() {
        Log.i("123","123")
        val db = FirebaseFirestore.getInstance()
        val query = db.collection("Canteen").document("1groVeeLm07ilrWRo0il")
            .collection("Store").orderBy("storeName", Query.Direction.ASCENDING)


        val options =
            FirestoreRecyclerOptions.Builder<CanteenStore>()
                .setQuery(query, CanteenStore::class.java).build()


        adapter = StoreFirestoreAdapter(options, this, context!!)
        binding.rvCanteenStore.layoutManager = LinearLayoutManager(activity)
        binding.rvCanteenStore.adapter = adapter

    }

    override fun onItemClick(store: CanteenStore, position: Int) {
        Log.i("123", store.storeName)
        this.findNavController()
            .navigate(CanteenStoreFragmentDirections.actionCanteenStoreFragmentToFoodFragment())

    }


    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter?.stopListening()
        }
    }

}

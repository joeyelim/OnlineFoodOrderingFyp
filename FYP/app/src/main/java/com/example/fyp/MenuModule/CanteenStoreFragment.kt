package com.example.fyp.MenuModule


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
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

        initRecycleView()


        return binding.root
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

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

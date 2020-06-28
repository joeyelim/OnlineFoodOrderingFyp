package com.example.fyp.OrderingModule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fyp.MainActivity

import com.example.fyp.databinding.FragmentCurrentOrderBinding

import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Class.CanteenStore
import com.example.fyp.Class.Order
import com.example.fyp.Class.Order_Food
import com.example.fyp.FirestoreAdapter.CurrentOrderOuterAdapter
import com.example.fyp.FirestoreAdapter.StoreFirestoreAdapter
import com.example.fyp.FirestoreAdapter.onListClick2
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class CurrentOrderFragment : Fragment(), onListClick2 {

    private lateinit var binding: FragmentCurrentOrderBinding
//    private lateinit var adapter: CurrentOrderFireStoreAdapter
    private lateinit var adapter: CurrentOrderOuterAdapter
    private val ARG_PLAYERS = "arg_player"
    private var player : String = "13"

    /* --------this is for viewpage------------------*/
    fun newInstance(players: String): CurrentOrderFragment {

        val args = Bundle()
        args.putString(ARG_PLAYERS, players)
        val fragment = CurrentOrderFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            this.player = args.getString(ARG_PLAYERS, "123")
        }
        requireNotNull(player) { "Player list can not be null" }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_current_order, container, false
        )

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavVisible()

//        var b: TextView = view.findViewById(com.example.fyp.R.id.textView5)
//        b.text = abba1

        initRecycleView()


        return binding.root
    }


    /* --------this is for viewpage------------------*/

    private fun initRecycleView() {

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("User").document("limye-wm18@student.tarc.edu.my")
            .collection("Order").orderBy("id", Query.Direction.ASCENDING)
            .whereEqualTo("status", player)


        query.get()
            .addOnSuccessListener {
                for (item in it.documents) {
                    Log.i("Test", item.id)
                }
            }
            .addOnFailureListener {
                Log.i("Test", player)
                Log.i("Test", "Fail")
            }

        val options =
            FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query, Order::class.java).build()

        adapter = CurrentOrderOuterAdapter(options, this, context!!)
        binding.currentOrderRecycle.layoutManager = LinearLayoutManager(context)
        binding.currentOrderRecycle.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()

        adapter.stopListening()
    }



}
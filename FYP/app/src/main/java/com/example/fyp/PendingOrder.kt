package com.example.fyp


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Class.Order_Food
import com.example.fyp.FirestoreAdapter.OrderListFireStoreAdapter
import com.example.fyp.Interface.OnCurrentOrderAdapterClick
import com.example.fyp.ViewModel.CartViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentPendingOrderBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class PendingOrder : Fragment(), OnCurrentOrderAdapterClick {
    private lateinit var binding: FragmentPendingOrderBinding
    private lateinit var adapter: OrderListFireStoreAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_pending_order, container, false
        )

        setHasOptionsMenu(true)
        (activity as MainActivity).setNavVisible()
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        cartViewModel = ViewModelProviders.of(activity!!).get(CartViewModel::class.java)

        if (userViewModel.user!!.email != "") {
            initRecycleView()
        }

        return binding.root
    }

    private fun initRecycleView() {
        try {
            // staff view
            if (userViewModel.user?.role == "staff") {
                val db = FirebaseFirestore.getInstance()
                val query = db.collection("Canteen").document(userViewModel.user?.canteen!!)
                    .collection("Store").document(userViewModel.user?.store!!)
                    .collection("Order_Food")
                    .whereEqualTo("status", "Pending")

                query.get()
                    .addOnFailureListener {
                        Log.i("Test", "Fail")
                    }

                query.addSnapshotListener { p0, _ ->
                    if (p0 != null) {
                        if (p0.size() > 0) {
                            binding.txtempty.visibility = View.GONE
                            binding.pendingOrderRecycleView.visibility = View.VISIBLE
                        } else {
                            binding.txtempty.visibility = View.VISIBLE
                            binding.pendingOrderRecycleView.visibility = View.GONE
                            binding.txtempty.setText("There are currently no orders in here.")
                        }
                    }
                }

                val options =
                    FirestoreRecyclerOptions.Builder<Order_Food>()
                        .setQuery(query, Order_Food::class.java).build()

                adapter =
                    OrderListFireStoreAdapter(options, this, context!!, userViewModel.user!!)
                binding.pendingOrderRecycleView.layoutManager = LinearLayoutManager(context)
                binding.pendingOrderRecycleView.adapter = adapter
                binding.pendingOrderRecycleView.isNestedScrollingEnabled = true
            }
            else
            {

                // student view

                val db = FirebaseFirestore.getInstance()
                val query = db.collection("User").document(userViewModel.user?.email!!)
                    .collection("Order_Food")
                    .whereEqualTo("status", "Pending")

                query.addSnapshotListener { p0, _ ->
                    if (p0 != null) {

                        if (p0.size() > 0) {
                            binding.txtempty.visibility = View.GONE
                            binding.pendingOrderRecycleView.visibility = View.VISIBLE
                        } else {
                            binding.txtempty.visibility = View.VISIBLE
                            binding.pendingOrderRecycleView.visibility = View.GONE
                            binding.txtempty.setText("There are currently no orders in here.")
                        }
                    }
                }

                val options =
                    FirestoreRecyclerOptions.Builder<Order_Food>()
                        .setQuery(query, Order_Food::class.java).build()

                adapter =
                    OrderListFireStoreAdapter(options, this, context!!, userViewModel.user!!)
                binding.pendingOrderRecycleView.layoutManager = LinearLayoutManager(context)
                binding.pendingOrderRecycleView.adapter = adapter
            }


        } catch (e: Exception) {

        }
    }

    override fun buttonClick(order: Order_Food) {
        val db = FirebaseFirestore.getInstance()
        val r1 =
            db.collection("User").document(order.email!!)
                .collection("Order_Food").document(order.id!!)
        val r2 =
            db.collection("Canteen").document(order.canteen_Name!!)
                .collection("Store").document(order.store_Name!!)
                .collection("Order_Food").document(order.id!!)

        if (userViewModel.user!!.role == "staff") {
            db.runBatch {
                it.update(r1,"status", "Preparing")
                it.update(r2,"status", "Preparing")
            }.addOnFailureListener {
                Log.i("Test", it.toString())
            }

                .addOnCompleteListener {
                Toast.makeText(activity, "Order Is Preparing! ", Toast.LENGTH_LONG).show()
            }
        } else {
            db.runBatch {
                it.delete(r1)
                it.delete(r2)
            }.addOnCompleteListener {
                Toast.makeText(activity, "Order Being Removed! ", Toast.LENGTH_LONG).show()
            }
        }


    }

    override fun onStart() {
        super.onStart()
        if (userViewModel.user!!.email != "") {
            adapter.startListening()
        }

    }

    override fun onStop() {
        super.onStop()
        if (userViewModel.user!!.email != "") {
            adapter.stopListening()
        }
    }


}

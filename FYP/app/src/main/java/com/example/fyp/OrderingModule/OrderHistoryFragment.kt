package com.example.fyp.OrderingModule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Class.Order
import com.example.fyp.Class.Order_Food
import com.example.fyp.FirestoreAdapter.OrderHistoryFirestoreAdapter
import com.example.fyp.FirestoreAdapter.OrderHistoryViewHolder
import com.example.fyp.MainActivity
import com.example.fyp.ViewModel.CartViewModel
import com.example.fyp.ViewModel.UserViewModel
import com.example.fyp.databinding.FragmentOrderHistoryBinding
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore



class OrderHistoryFragment : Fragment(), OrderHistoryViewHolder.onListClick5 {

    private lateinit var binding: FragmentOrderHistoryBinding
    private lateinit var adapter: OrderHistoryFirestoreAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var cartViewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, com.example.fyp.R.layout.fragment_order_history, container, false
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
            val db = FirebaseFirestore.getInstance()
            val query = db.collection("User").document(userViewModel.user?.email!!)
                .collection("Order_Food")
                .whereEqualTo("status", "Paid")
//            val query2 = collectionRef.whereEqualTo("status", "Cancel")

            query.addSnapshotListener { p0, _ ->
                if (p0 != null) {

                    if(p0.size() > 0) {
                        binding.txtOrderHistoryEmpty.visibility = View.GONE
                        binding.rvOrderHistory.visibility = View.VISIBLE
                    }
                    else {
                        binding.txtOrderHistoryEmpty.visibility = View.VISIBLE
                        binding.rvOrderHistory.visibility = View.GONE
                        binding.txtOrderHistoryEmpty.setText("There are currently no order history in here.")
                    }
                }
            }


            val options =
                FirestoreRecyclerOptions.Builder<Order_Food>()
                    .setQuery(query, Order_Food::class.java).build()

            adapter =
                OrderHistoryFirestoreAdapter(options, this, context!!)
            binding.rvOrderHistory.layoutManager = LinearLayoutManager(context)
            binding.rvOrderHistory.adapter = adapter
            binding.rvOrderHistory.isNestedScrollingEnabled = true
        } catch (e: Exception) {

        }
    }

//    override fun onItemClick(order: Order_Food, position: Int) {
////        userViewModel.order = order
//        this.findNavController()
//            .navigate(OrderHistoryFragmentDirections.actionOrderHistoryFragmentToOrderDetailsFragment())
//    }


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
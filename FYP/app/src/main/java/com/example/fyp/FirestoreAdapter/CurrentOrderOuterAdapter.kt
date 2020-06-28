package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Canteen
import com.example.fyp.Class.Order
import com.example.fyp.Class.Order_Food
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_current_order.view.*
import kotlinx.android.synthetic.main.outer_recycle_view_layout.view.*

class CurrentOrderOuterAdapter(
    options: FirestoreRecyclerOptions<Order>, var onListClick1: onListClick2, var context: Context
) :
    FirestoreRecyclerAdapter<Order, CurrentOrderOuterViewHolder>(options), onListClick2 {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentOrderOuterViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.outer_recycle_view_layout, parent, false)
        return CurrentOrderOuterViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CurrentOrderOuterViewHolder, position: Int, model: Order) {
        holder.setCanteenState(model, onListClick1, holder)
    }

}

class CurrentOrderOuterViewHolder internal constructor( val view: View, var context: Context) :
    RecyclerView.ViewHolder(view), onListClick2 {
    

    internal fun setCanteenState(order: Order, onListClick1: onListClick2, holder: CurrentOrderOuterViewHolder) {
        lateinit var dataListener : ListenerRegistration

        holder.view.currentOrderTimeTitle.text = "Booking Time : " + order.pickUp_Date + " " + order.pickUp_Time

//        val db = FirebaseFirestore.getInstance()
//        val query = db.collection("User").document("Yong Boon")
//            .collection("Order").document(order.id!!)
//            .collection("Order_Food").orderBy("food_Name", Query.Direction.ASCENDING)
//
//
////        Toast.makeText(activity, player, Toast.LENGTH_SHORT).show()
//
//        val options =
//            FirestoreRecyclerOptions.Builder<Order_Food>()
//                .setQuery(query, Order_Food::class.java).build()
//
//        adapter = CurrentOrderFireStoreAdapter(options, this, context)
//
//        holder.view.InnerRecycleView.layoutManager = LinearLayoutManager(context)
//
//        holder.view.InnerRecycleView.adapter = adapter

        val db = FirebaseFirestore.getInstance()
        val query = db.collection("User").document("limye-wm18@student.tarc.edu.my")
            .collection("Order").document(order.id!!)
            .collection("Order_Food")

        dataListener = query
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    Log.d("123", "Current data: ${snapshot.toObjects(Order_Food::class.java)}")
                    val adapter = snapshot.toObjects(Order_Food::class.java)
                    val adapter2 = TestAdapter()
                    holder.view.InnerRecycleView.layoutManager = LinearLayoutManager(context)
                    holder.view.InnerRecycleView.adapter = adapter2
                    adapter2.data = adapter
                } else {
                    Log.d("123", "Current data: null")
                }
            }
    }
}
package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Notification
import com.example.fyp.Class.Order_Food
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.fragment_order_history.view.*
import kotlinx.android.synthetic.main.order_history_row.view.*
import java.text.DecimalFormat

class OrderHistoryFirestoreAdapter (
    options: FirestoreRecyclerOptions<Order_Food>, var onListClick1: onListClick2, var context: Context
) :
    FirestoreRecyclerAdapter<Order_Food, OrderHistoryViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_history_row, parent, false)
        return OrderHistoryViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int, model: Order_Food) {
        holder.setCanteenState(model, onListClick1, holder)

    }

}

private fun getPrice(food : Order_Food) : Double {

    return food.each_Price !!* (food.quantity)!!.toDouble()
}

class OrderHistoryViewHolder internal constructor(private val view: View, var context: Context) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(order: Order_Food, onListClick1: onListClick2, holder: OrderHistoryViewHolder) {
//        val food : Order_Food = data[position]
        val dec = DecimalFormat("RM ###.00")

        holder.view.txtfoodName.text = order.food_Name
        holder.view.txtPrice.text = dec.format(getPrice(order)).toString()
        holder.view.txtStatus.text = order.status
        holder.view.txtOrderDate.text = order.pickUp_Date

//        holder.view.rvOrderHistory.setOnClickListener {
//            onListClick1.onItemClick(order, adapterPosition)
//        }
    }

//    interface onListClick2 {
//        fun onItemClick(order: Order_Food, position: Int) {
//
//        }
//    }

}

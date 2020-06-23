package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Order_Food
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.order_list_row.view.*


class CurrentOrderFireStoreAdapter(
    options: FirestoreRecyclerOptions<Order_Food>, var onListClick1: onListClick2, var context: Context
) :
    FirestoreRecyclerAdapter<Order_Food, CurrentOrderViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentOrderViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_list_row, parent, false)
        return CurrentOrderViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CurrentOrderViewHolder, position: Int, model: Order_Food) {
        holder.setCanteenState(model, onListClick1, holder)
    }

}

class CurrentOrderViewHolder internal constructor(private val view: View, var context: Context) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(food: Order_Food, onListClick1: onListClick2, holder: CurrentOrderViewHolder) {
        holder.view.txtCanteenName.text = food.canteen_Name
        holder.view.txtStore.text = food.store_Name
        holder.view.food.text = food.food_Name
        holder.view.txtQuantity.text = food.quantity.toString()
        holder.view.txtOption.text = "takeAway"
        holder.view.txtPrice.text = "RM " + food.each_Price.toString()
        holder.view.txtProgress.text = food.status

        Toast.makeText(context, food.status, Toast.LENGTH_SHORT).show()

        when(food.status) {
            "Pending" -> holder.view.txtProgress.setTextColor(Color.RED)
            "Preparing" -> holder.view.txtProgress.setTextColor(Color.BLUE)
            "Ready" -> holder.view.txtProgress.setTextColor(Color.CYAN)
        }

    }
}

//interface onListClick {
//    fun onItemClick(food: Food, position: Int) {
//
//    }
//}
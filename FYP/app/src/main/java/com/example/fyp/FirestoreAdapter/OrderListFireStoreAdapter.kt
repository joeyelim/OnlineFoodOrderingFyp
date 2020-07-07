package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Food
import com.example.fyp.Class.Order
import com.example.fyp.Class.Order_Food
import com.example.fyp.Interface.OnCurrentOrderAdapterClick
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.order_list_row.view.*
import java.text.DecimalFormat

class OrderListFireStoreAdapter(
    options: FirestoreRecyclerOptions<Order_Food>, var itemClick: OnCurrentOrderAdapterClick, var context: Context
) :
    FirestoreRecyclerAdapter<Order_Food, OrderListViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_list_row, parent, false)
        return OrderListViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int, model: Order_Food) {
        holder.setCanteenState(model, itemClick, holder)

    }

}

class OrderListViewHolder internal constructor(private val view: View, var context: Context) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(order: Order_Food, itemClick: OnCurrentOrderAdapterClick, holder: OrderListViewHolder) {
//        val food : Order_Food = data[position]
        val dec = DecimalFormat("RM ###.00")

        holder.view.txtCanteenName.text = order.canteen_Name
        holder.view.txtStore.text = order.store_Name
        holder.view.food.text = order.food_Name
        holder.view.txtQuantity.text = order.quantity.toString()
        holder.view.txtOption.text = order.dining_option
        holder.view.txtPrice.text = dec.format(getPrice(order)).toString()
        holder.view.txtProgress.text = order.status
        holder.view.txtRemarks.text = order.remark
        holder.view.orderListDate.text = order.pickUp_Date
        holder.view.orderListTime.text = order.pickUp_Time

        holder.view.btnOrderCancel.setOnClickListener {
            itemClick.buttonClick(order)
        }

        when(order.status) {
            "Pending" -> pendingUI(holder)
            "Preparing" -> preparingUI(holder)
            "Ready" -> readyUI(holder)
//            "Taken" -> takenUI(holder)
        }
    }

    private fun getPrice(food : Order_Food) : Double {

        return food.each_Price !!* (food.quantity)!!.toDouble()
    }

    private fun pendingUI(holder: OrderListViewHolder) {
        holder.view.btnOrderCancel.visibility = View.VISIBLE
        holder.view.txtProgress.setTextColor(Color.parseColor("#FF7A7B"))
        holder.view.btnOrderCancel.setTextColor(Color.WHITE)
        holder.view.btnOrderCancel.setBackgroundColor(Color.rgb(125,4,4))
    }

    private fun preparingUI(holder: OrderListViewHolder){
        holder.view.btnOrderCancel.visibility = View.GONE
        holder.view.txtProgress.setTextColor(Color.parseColor("#034AFF"))
    }

    private fun readyUI(holder: OrderListViewHolder) {
        holder.view.btnOrderCancel.visibility = View.VISIBLE
        holder.view.btnOrderCancel.text = "Paid"
        holder.view.btnOrderCancel.setTextColor(Color.WHITE)
        holder.view.btnOrderCancel.setBackgroundColor(Color.parseColor("#034AFF"))
        holder.view.btnOrderCancel.getPaint().setColor(Color.WHITE)
        holder.view.btnOrderCancel.getPaint().setStyle(Paint.Style.STROKE)
        holder.view.txtProgress.setTextColor(Color.parseColor("#04C852"))
    }

//    private fun takenUI(holder: OrderListViewHolder) {
//        holder.view.btnOrderCancel.visibility = View.GONE
//        holder.view.txtProgress.setTextColor(Color.rgb(49,151,140))
//    }
}
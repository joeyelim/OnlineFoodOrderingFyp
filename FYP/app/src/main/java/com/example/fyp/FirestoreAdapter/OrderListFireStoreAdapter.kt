package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Order_Food
import com.example.fyp.Class.User
import com.example.fyp.Interface.OnCurrentOrderAdapterClick
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.order_list_row.view.*
import java.text.DecimalFormat

class OrderListFireStoreAdapter(
    options: FirestoreRecyclerOptions<Order_Food>, var itemClick: OnCurrentOrderAdapterClick,
    var context: Context, val user: User
) :
    FirestoreRecyclerAdapter<Order_Food, OrderListViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderListViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_list_row, parent, false)
        return OrderListViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: OrderListViewHolder, position: Int, model: Order_Food) {
        holder.setCanteenState(model, itemClick, holder, user)

    }

}

class OrderListViewHolder internal constructor(private val view: View, var context: Context) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(
        order: Order_Food, itemClick: OnCurrentOrderAdapterClick,
        holder: OrderListViewHolder, user: User
    ) {
//        val food : Order_Food = data[position]
        val dec = DecimalFormat("RM ###.00")

        if (user.role == "staff") {
            holder.view.txtCanteenName.text = order.email
        } else {
            holder.view.txtCanteenName.text = order.canteen_Name
            holder.view.txtStore.text = order.store_Name
        }

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

        when (order.status) {
            "Pending" -> pendingUI(holder, user)
            "Preparing" -> preparingUI(holder, user)
            "Ready" -> readyUI(holder, user)
//            "Taken" -> takenUI(holder)
        }
    }

    private fun getPrice(food: Order_Food): Double {

        return food.each_Price!! * (food.quantity)!!.toDouble()
    }

    private fun pendingUI(holder: OrderListViewHolder,  user: User) {
        if (user.role == "staff") {
            holder.view.btnOrderCancel.text = "Prepare"
        }

        holder.view.btnOrderCancel.visibility = View.VISIBLE
        holder.view.txtProgress.setTextColor(Color.parseColor("#FF7A7B"))
        holder.view.btnOrderCancel.setTextColor(Color.WHITE)
        holder.view.btnOrderCancel.setBackgroundColor(Color.rgb(125, 4, 4))
    }

    private fun preparingUI(holder: OrderListViewHolder, user: User) {
        if (user.role == "staff") {
            holder.view.btnOrderCancel.visibility = View.VISIBLE
            holder.view.btnOrderCancel.text = "Ready"
            holder.view.btnOrderCancel.setBackgroundColor(Color.parseColor("#034AFF"))
            holder.view.btnOrderCancel.setTextColor(Color.WHITE)
        }else {
            holder.view.btnOrderCancel.visibility = View.GONE
        }

        holder.view.txtProgress.setTextColor(Color.parseColor("#034AFF"))
    }

    private fun readyUI(holder: OrderListViewHolder,  user: User) {
        if (user.role == "staff") {
            holder.view.btnOrderCancel.visibility = View.VISIBLE
            holder.view.btnOrderCancel.text = "Paid"
        } else {
            holder.view.btnOrderCancel.visibility = View.GONE
        }
        holder.view.btnOrderCancel.setTextColor(Color.WHITE)
        holder.view.btnOrderCancel.setBackgroundColor(Color.parseColor("#04C852"))
//        holder.view.btnOrderCancel.getPaint().setColor(Color.WHITE)
//        holder.view.btnOrderCancel.getPaint().setStyle(Paint.Style.STROKE)
        holder.view.txtProgress.setTextColor(Color.parseColor("#04C852"))
    }

//    private fun takenUI(holder: OrderListViewHolder) {
//        holder.view.btnOrderCancel.visibility = View.GONE
//        holder.view.txtProgress.setTextColor(Color.rgb(49,151,140))
//    }
}
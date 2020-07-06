package com.example.fyp.FirestoreAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Cart
import com.example.fyp.R
import kotlinx.android.synthetic.main.place_order_food_row.view.*
import java.text.DecimalFormat

class PlaceOrderItemAdapter : RecyclerView.Adapter<PlaceOrderItemAdapter.PlaceItemViewHolder>() {

    var data = ArrayList<Cart>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.place_order_food_row, parent, false)
        return PlaceItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(
        holder: PlaceItemViewHolder,
        position: Int
    ) {
        holder.view.txtOrderFoodName.text = data[position].food_name
        holder.view.txtOrderRemarks.text = data[position].remark
        holder.view.txtOrderQty.text = data[position].quantity.toString()
        holder.view.txtOrderFoodPrice.text = DecimalFormat("RM ###.00")
            .format(data[position].quantity!! * data[position].each_price!!).toString()
    }

    class PlaceItemViewHolder(val view : View) : RecyclerView.ViewHolder(view)

}


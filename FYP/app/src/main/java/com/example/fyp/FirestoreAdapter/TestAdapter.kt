package com.example.fyp.FirestoreAdapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Order_Food
import com.example.fyp.R
import com.google.protobuf.Int32Value
import kotlinx.android.synthetic.main.order_list_row.view.*
import kotlinx.android.synthetic.main.outer_recycle_view_layout.view.*

class TestAdapter () : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    var data = listOf<Order_Food>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapter.TestViewHolder {
        Log.i("Test", "here")
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.order_list_row, parent, false)
        return TestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        val food : Order_Food = data[position]

        holder.view.txtCanteenName.text = food.canteen_Name
        holder.view.txtStore.text = food.store_Name
        holder.view.food.text = food.food_Name
        holder.view.txtQuantity.text = food.quantity.toString()
        holder.view.txtOption.text = food.option
        holder.view.txtPrice.text = getPrice(food).toString()
        holder.view.txtProgress.text = food.status
        holder.view.txtRemarks.text = food.remark

        when(food.status) {
            "Pending" -> pendingUI(holder)
            "Preparing" -> preparingUI(holder)
            "Ready" -> readyUI(holder)
            "Taken" -> takenUI(holder)
        }
    }

    private fun getPrice(food : Order_Food) : Double {
        return food.each_Price !!* (food.quantity)!!.toDouble()
    }

    private fun pendingUI(holder: TestViewHolder) {
        holder.view.btnOrderCancel.visibility = View.VISIBLE
        holder.view.txtProgress.setTextColor(Color.RED)
        holder.view.btnOrderCancel.setTextColor(Color.WHITE)
        holder.view.btnOrderCancel.setBackgroundColor(Color.rgb(125,4,4))
    }

    private fun preparingUI(holder: TestViewHolder){
        holder.view.btnOrderCancel.visibility = View.GONE
        holder.view.txtProgress.setTextColor(Color.BLUE)
    }

    private fun readyUI(holder: TestViewHolder) {
        holder.view.btnOrderCancel.visibility = View.VISIBLE
        holder.view.btnOrderCancel.text = "Paid"
        holder.view.btnOrderCancel.setTextColor(Color.WHITE)
        holder.view.btnOrderCancel.setBackgroundColor(Color.BLUE)
        holder.view.txtProgress.setTextColor(Color.rgb(4,200,82))
    }

    private fun takenUI(holder: TestViewHolder) {
        holder.view.btnOrderCancel.visibility = View.GONE
        holder.view.txtProgress.setTextColor(Color.rgb(49,151,140))
    }


    class TestViewHolder(val view : View) : RecyclerView.ViewHolder(view)

}
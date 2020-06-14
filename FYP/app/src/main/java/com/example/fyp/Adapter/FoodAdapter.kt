package com.example.fyp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Food
import kotlinx.android.synthetic.main.canteen_store_row.view.*


class FoodAdapter: RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    var data = listOf<Food>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(food: Food) {
            itemView.txtStoreName.text = food.food_name

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater
            .inflate(com.example.fyp.R.layout.food_entry, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(data[position])
    }
}
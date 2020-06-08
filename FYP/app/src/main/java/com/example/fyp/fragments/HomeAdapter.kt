package com.example.fyp.fragments

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Canteen

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var data = listOf<Canteen>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater
            .inflate(com.example.fyp.R.layout.canteen_row, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        val item: Canteen = data[position]
        var content : String = ""

        holder.name.text = item.canteenName
        holder.type.text = item.type.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(com.example.fyp.R.id.txtCanteen)
        val type: TextView = itemView.findViewById(com.example.fyp.R.id.txtDescription)

    }

}
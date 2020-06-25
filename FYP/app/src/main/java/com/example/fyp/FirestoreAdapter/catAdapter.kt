package com.example.fyp.FirestoreAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.MyAdapter
import com.example.fyp.R
import kotlinx.android.synthetic.main.food_category_row.view.*

class catAdapter(private val dataSet : MutableList<String>) : RecyclerView.Adapter<catAdapter.CatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): catAdapter.CatViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.food_category_row, parent, false)
        return CatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: catAdapter.CatViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    class CatViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {
        // Create new views (invoked by the layout manager)

        fun bind(item : String) {
            view.catText.text = item
        }
        
    }
}


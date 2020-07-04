package com.example.fyp.FirestoreAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
            if (view.catText.text=="Noodle"){
                view.ic_category.setBackgroundResource(R.drawable.ic_noodles1)
            }
            if (view.catText.text=="Spicy"){
                view.ic_category.setBackgroundResource(R.drawable.ic_hot_chili)
            }
            if (view.catText.text=="Soup"){
                view.ic_category.setBackgroundResource(R.drawable.ic_hot_soup)
            }
            if (view.catText.text=="Rice"){
                view.ic_category.setBackgroundResource(R.drawable.ic_rice)
            }
            if (view.catText.text=="Vegetarian"){
                view.ic_category.setBackgroundResource(R.drawable.ic_vegetarian)
            }
            if (view.catText.text=="Beverage"){
                view.ic_category.setBackgroundResource(R.drawable.ic_beverage)
            }
        }
        
    }
}


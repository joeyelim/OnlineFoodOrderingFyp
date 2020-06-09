package com.example.fyp.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Canteen
import kotlinx.android.synthetic.main.canteen_row.view.*

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
//        val item: Canteen = data[position]
//        var content: String = ""
        holder.bindItems(data[position])

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(canteen: Canteen) {
            itemView.txtCanteen.text = canteen.canteenName
            itemView.txtDescription.text = canteen.type.toString()
        }

    }

}
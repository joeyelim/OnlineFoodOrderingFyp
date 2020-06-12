package com.example.fyp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Canteen
import com.example.fyp.Class.CanteenStore
import kotlinx.android.synthetic.main.canteen_row.view.*
import kotlinx.android.synthetic.main.canteen_store_row.view.*


class CanteenStoreAdapter: RecyclerView.Adapter<CanteenStoreAdapter.ViewHolder>() {

    var data = listOf<CanteenStore>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(store: CanteenStore) {
            itemView.txtStoreName.text = store.storeName
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater
            .inflate(com.example.fyp.R.layout.canteen_store_row, parent, false) as View

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(data[position])
    }


}
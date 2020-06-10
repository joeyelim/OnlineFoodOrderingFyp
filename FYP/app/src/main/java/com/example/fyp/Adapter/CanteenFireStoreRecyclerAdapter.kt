package com.example.fyp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Canteen
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class CanteenFireStoreRecyclerAdapter(options: FirestoreRecyclerOptions<Canteen>, var onListClick2: onListClick) :
    FirestoreRecyclerAdapter<Canteen, CanteenViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanteenViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.canteen_row, parent, false)
        return CanteenViewHolder(view)
    }

    override fun onBindViewHolder(holder: CanteenViewHolder, position: Int, model: Canteen) {
        holder.setCanteenState(model, onListClick2)
    }

}

class CanteenViewHolder internal constructor(private val view: View) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(canteen: Canteen, onListClick2: onListClick) {
        val canteenName = view.findViewById<TextView>(R.id.txtCanteen)
        canteenName.text = canteen.canteenName
        val canteenDesciption = view.findViewById<TextView>(R.id.txtDescription)
        canteenDesciption.text = canteen.type.toString()

//        val button = view.findViewById<ImageView>(R.id.imgIcon)
////        button.setOnClickListener(this)
        itemView.setOnClickListener{
            onListClick2.onItemClick(canteen, adapterPosition)
        }

        val image = view.findViewById<ImageView>(R.id.imgIcon)
        image.setOnClickListener {
            onListClick2.onItemClick(canteen, adapterPosition)
        }
    }
}

interface onListClick{
    fun onItemClick(canteen : Canteen, position : Int)
}
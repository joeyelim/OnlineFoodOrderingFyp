package com.example.fyp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fyp.Class.Canteen
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso

class CanteenFireStoreRecyclerAdapter(options: FirestoreRecyclerOptions<Canteen>, var onListClick2: onListClick
, var context : Context) :
    FirestoreRecyclerAdapter<Canteen, CanteenViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanteenViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.canteen_row, parent, false)
        return CanteenViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CanteenViewHolder, position: Int, model: Canteen) {
        holder.setCanteenState(model, onListClick2)
    }

}

class CanteenViewHolder internal constructor(private val view: View, var context : Context) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(canteen: Canteen, onListClick2: onListClick) {
        val canteenName = view.findViewById<TextView>(R.id.txtCanteen)
        canteenName.text = canteen.canteenName
        val canteenDesciption = view.findViewById<TextView>(R.id.txtDescription)
        canteenDesciption.text = canteen.type.toString()


        view.setOnClickListener{
            onListClick2.onItemClick(canteen, adapterPosition)
        }

        val image = view.findViewById<ImageView>(R.id.imgCanteen)
        val a = FirebaseStorage.getInstance().getReference(canteen.image!!)

        a.downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .into(image)
        }
    }
}

interface onListClick{
    fun onItemClick(canteen : Canteen, position : Int)
}
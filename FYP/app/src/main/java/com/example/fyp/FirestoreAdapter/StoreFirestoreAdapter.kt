package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.CanteenStore
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.card.MaterialCardView
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class StoreFirestoreAdapter(options: FirestoreRecyclerOptions<CanteenStore>, var onListClick: onListClick1
                            , var context : Context
) :
    FirestoreRecyclerAdapter<CanteenStore, CanteenStoreViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanteenStoreViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.canteen_store_row, parent, false)
        return CanteenStoreViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: CanteenStoreViewHolder, position: Int, model: CanteenStore) {
        holder.setCanteenState(model, onListClick, holder)

    }

}

class CanteenStoreViewHolder internal constructor(private val view: View, var context : Context) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(store: CanteenStore, onListClick: onListClick1, holder : CanteenStoreViewHolder) {
        val storeName = view.findViewById<TextView>(R.id.txtStoreName)
        storeName.text = store.store_name

//        val canteen = view.findViewById<TextView>(R.id.canteen)
//        canteen.text = store.canteen



        val image = view.findViewById<ImageView>(R.id.imgStore)
//        val imgcanteen = view.findViewById<ImageView>(R.id.canteenImage)

        val a = FirebaseStorage.getInstance().getReference(store.store_image!!)


        image.setOnClickListener {
            onListClick.onItemClick(store, adapterPosition)
        }




        a.downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .into(image)
        }
    }
}

interface onListClick1{
    fun onItemClick(store : CanteenStore, position : Int){

    }
}
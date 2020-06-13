package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Canteen
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
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
        holder.setCanteenState(model, onListClick2, holder)

    }

}

class CanteenViewHolder internal constructor(private val view: View, var context : Context) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(canteen: Canteen, onListClick2: onListClick, holder : CanteenViewHolder) {
        val canteenName = view.findViewById<TextView>(R.id.txtCanteen)
        canteenName.text = canteen.canteen_name
        val canteenDesciption = view.findViewById<TextView>(R.id.txtDescription)
        canteenDesciption.text = canteen.time.toString()
        

//        view.setOnClickListener{
//            onListClick2.onItemClick(canteen, adapterPosition)
//        }

        val icon = view.findViewById<ImageView>(R.id.imgIcon)
        val title = view.findViewById<RelativeLayout>(R.id.cardTitle)
        val image = view.findViewById<ImageView>(R.id.imgCanteen)


        title.setOnClickListener {
            onListClick2.onItemClick(canteen, adapterPosition)
        }

        icon.setOnClickListener {
            onListClick2.onItemClick(canteen, adapterPosition)
        }
        image.setOnClickListener {
            onListClick2.onItemClick(canteen, adapterPosition)
        }

        val a = FirebaseStorage.getInstance().getReference(canteen.image!!)

        a.downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .into(image)
        }
    }
}

interface onListClick{
    fun onItemClick(canteen : Canteen, position : Int){



    }
}
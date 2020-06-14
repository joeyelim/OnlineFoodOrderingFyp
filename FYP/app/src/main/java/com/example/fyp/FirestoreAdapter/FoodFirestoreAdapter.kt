package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Food
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class FoodFirestoreAdapter(
    options: FirestoreRecyclerOptions<Food>, var onListClick1: onListClick2, var context: Context
) :
    FirestoreRecyclerAdapter<Food, FoodViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.food_entry, parent, false)
        return FoodViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int, model: Food) {
        holder.setCanteenState(model, onListClick1, holder)

    }

}

class FoodViewHolder internal constructor(private val view: View, var context: Context) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(food: Food, onListClick1: onListClick2, holder: FoodViewHolder) {
        val foodName = view.findViewById<TextView>(R.id.tvFoodName)
        foodName.text = food.food_name

        val price = view.findViewById<TextView >(R.id.tvPrice)
        price.text = food.price.toString()
        
        val image = view.findViewById<ImageView>(R.id.imgFood)
        val a = FirebaseStorage.getInstance().getReference(food.food_image!!)

        image.setOnClickListener {
            onListClick1.onItemClick(food, adapterPosition)
        }


        a.downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .into(image)
        }
    }
}

interface onListClick2 {
    fun onItemClick(food: Food, position: Int) {

    }
}
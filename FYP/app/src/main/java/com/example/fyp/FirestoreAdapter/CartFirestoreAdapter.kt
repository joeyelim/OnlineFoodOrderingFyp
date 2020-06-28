package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.CanteenStore
import com.example.fyp.Class.Cart
import com.example.fyp.MainActivity
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_row.view.*
import java.text.DecimalFormat

class CartFirestoreAdapter(
    options: FirestoreRecyclerOptions<Cart>, var onListClick: onListClick3, var context: Context
) :
    FirestoreRecyclerAdapter<Cart, CartViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_row, parent, false)
        return CartViewHolder(view, context)
    }

    override fun onBindViewHolder(
        holder: CartViewHolder,
        position: Int,
        model: Cart
    ) {
        holder.setCanteenState(model, onListClick, holder)

    }

}

class CartViewHolder internal constructor(private val view: View, var context: Context) : RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(
        cart: Cart,
        onListClick: onListClick3,
        holder: CartViewHolder
    ) {
        val dec = DecimalFormat("RM ###.00")

        holder.view.canteenName.text = cart.canteen_name
        holder.view.shop.text = cart.store_name
        holder.view.foodName.text = cart.food_name
        holder.view.txtRemarks.text = cart.remark
        holder.view.quantity.text = cart.quantity.toString()
        holder.view.txtFoodPrice.text = dec.format(cart.each_price).toString()

        val foodName = view.findViewById<TextView>(R.id.foodName)

        foodName.setOnClickListener {
            onListClick.onItemClick(cart, adapterPosition)
        }



    }
}

interface onListClick3 {
    fun onItemClick(cart: Cart, position: Int) {

    }

}
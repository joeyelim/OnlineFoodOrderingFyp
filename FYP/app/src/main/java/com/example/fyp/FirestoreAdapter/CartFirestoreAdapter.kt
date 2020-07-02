package com.example.fyp.FirestoreAdapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.CanteenStore
import com.example.fyp.Class.Cart
import com.example.fyp.Class.Ultility.Companion.delDialog
import com.example.fyp.Class.Ultility.Companion.openDialog
import com.example.fyp.MainActivity
import com.example.fyp.OrderingModule.AddToCartFragment
import com.example.fyp.R
import com.example.fyp.ViewModel.CanteenViewModel
import com.example.fyp.fragments.CartFragment
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

        val image = view.findViewById<ImageView>(R.id.imgFood)
        val a = FirebaseStorage.getInstance().getReference(cart.image!!)

        a.downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .into(image)
        }

//        var cartFragment:CartFragment? = null
//        var addToCartFragment:AddToCartFragment? = null

        holder.view.imgBtnDelete.setOnClickListener {
            delDialog(context, cart)
        }

        var counter = cart.quantity!!

        holder.view.btnPlus.setOnClickListener {
            val quantity = holder.view.quantity
            val totalStock = 5

            if (counter >= totalStock){
                // custom dialog
                openDialog(context)
                quantity.text = totalStock.toString()
            }
            else{
                counter++
                quantity.text = "$counter"
            }

        }

        holder.view.btnMinus.setOnClickListener {
            val quantity = holder.view.quantity
            if (counter < 1){
                quantity.text = "0"
            }
            else{
                counter--
                quantity.text = "$counter"
            }

        }
    }

//    fun delDialog(cart: Cart){
//        val dialog = AlertDialog.Builder(context)
//        val foodName: String? = cart.food_name
//
//        dialog.setTitle("Confirmation")
//        dialog.setMessage("Are you sure want to delete the order?\n* $foodName")
//        dialog.setPositiveButton("Yes") { _: DialogInterface, i: Int -> }
//        dialog.setNegativeButton("No") { _: DialogInterface, i: Int -> }
//        dialog.show()
//    }
//
//    fun openDialog(){
//        val dialog = AlertDialog.Builder(context)
//
//        dialog.setTitle("Oops, sorry!")
//        dialog.setMessage("Your order quantity has exceeded the maximum inventory, please select again.")
//        dialog.setPositiveButton("OK") { _: DialogInterface, i: Int -> }
//        dialog.show()
//    }

}



interface onListClick3 {
    fun onItemClick(cart: Cart, position: Int) {

    }

}
package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Cart
import com.example.fyp.Interface.OnAdapterItemClick
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.cart_row.view.*
import java.text.DecimalFormat

class CartFirestoreAdapter(
    options: FirestoreRecyclerOptions<Cart>,
    var onListClick: OnAdapterItemClick,
    var context: Context
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

class CartViewHolder internal constructor(private val view: View, var context: Context) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(
        cart: Cart,
        onListClick: OnAdapterItemClick,
        holder: CartViewHolder
    ) {
        val dec = DecimalFormat("RM ###.00")
        var totalQuantity = 1

        FirebaseFirestore.getInstance()
            .collection("Canteen").document(cart.canteen_name!!)
            .collection("Store").document(cart.store_name!!)
            .collection("Food").document(cart.food_name!!)
            .get()
            .addOnSuccessListener {
                val a = it.get("total_stock")
                if (a is Int) {
                    totalQuantity = a.toInt()
                }
            }

        holder.view.canteenName.text = cart.canteen_name
        holder.view.shop.text = cart.store_name
        holder.view.foodName.text = cart.food_name
        holder.view.txtRemarks.text = cart.remark
        holder.view.quantity.text = cart.quantity.toString()
        holder.view.txtFoodPrice.text = dec.format(cart.each_price!! * cart.quantity!!).toString()

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
            //            delDialog(context, cart)
            onListClick.deleteBtnClick(cart)
        }

        holder.view.btnPlus.setOnClickListener {
            onListClick.addBtnClick(cart, holder.view.quantity, holder.view.txtFoodPrice)
        }

        holder.view.btnMinus.setOnClickListener {
            onListClick.minusBtnClick(cart, holder.view.quantity, holder.view.txtFoodPrice)
        }

        holder.view.checkBox.setOnClickListener {
            onListClick.checkBoxClick(cart, holder.view.checkBox)
        }
    }

}

//interface onListClick3 {
//    fun onItemClick(cart: Cart, position: Int) {}
//
//}
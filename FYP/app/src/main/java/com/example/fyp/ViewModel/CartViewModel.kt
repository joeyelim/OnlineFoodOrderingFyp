package com.example.fyp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fyp.Class.Cart
import com.example.fyp.Class.Order
import com.example.fyp.Class.Order_Food
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CartViewModel : ViewModel() {
    private val _activeButton = MutableLiveData<Int>()
    val activeButton: LiveData<Int>
        get() = _activeButton

    var cartArrayList : ArrayList<Cart> = ArrayList()
    var order : Order = Order()
    var orderFood : ArrayList<Order_Food> = ArrayList()
    var pickUpTime : String = ""
    var option : String = ""


    fun activateCartButton() {
        _activeButton.value = _activeButton.value?.plus(1)
    }

    fun deActivateCartButton() {
        _activeButton.value = _activeButton.value?.minus(1)
    }

    fun resetCartButton() {
        _activeButton.value = 0
    }

    fun addItem(cart : Cart) {
//        hashMap[cart.cart_ID!!] = cart
        cartArrayList.add(cart)
    }

    fun removeItem(cart : Cart) {
//        hashMap.remove(cart.cart_ID)
        cartArrayList.remove(cart)
    }

    fun removeAll() {
//        hashMap = HashMap()
        order = Order()
        orderFood = ArrayList()
        cartArrayList = ArrayList()
    }

    fun setTime(time : String) {
        this.pickUpTime = time
    }

    fun initOrderFoodList(email : String?) {
        var i = 0

        for (item in cartArrayList) {
            val calForDate = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss") //or use getDateInstance()
            val saveCurrentDateTime = formatter.format(calForDate)
            val currentDate = SimpleDateFormat("dd.MM.yyyy").format(calForDate)
            val currentTime = SimpleDateFormat("HH:mm").format(calForDate)

            orderFood.add(Order_Food(item.food_name, item.each_price!!, "Pending",
                item.quantity, item.remark, item.canteen_name,
                item.store_name, option,("$saveCurrentDateTime $i"), currentDate,
                currentTime, pickUpTime, currentDate, email, item.image))

            i += 1
        }
    }


    fun getTotalPrice() : Float {
        var total : Float = "0.00".toFloat()

        for (item in cartArrayList) {
            total += (item.each_price!! * item.quantity!!).toFloat()
        }

        return total
    }


    init {
        _activeButton.value = 0
    }

}
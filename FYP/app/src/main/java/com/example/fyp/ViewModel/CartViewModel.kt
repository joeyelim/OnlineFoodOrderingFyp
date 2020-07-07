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

    var hashMap : HashMap<String, Cart> = HashMap()
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
        hashMap[cart.cart_ID!!] = cart
        cartArrayList.add(cart)
    }

    fun removeItem(cart : Cart) {
        hashMap.remove(cart.cart_ID)
        cartArrayList.remove(cart)
    }

    fun removeAll() {
        hashMap = HashMap()
        order = Order()
        orderFood = ArrayList()
        cartArrayList = ArrayList()
    }

//    fun setOrderValue( option : String) {
//        val date = Calendar.getInstance().time
//        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss") //get Order ID
//        val formatter2 = SimpleDateFormat("HH:mm") //get order Time
//        val formatter3 = SimpleDateFormat("dd.MM.yyyy") //get order Date
//        val formatedDate = formatter.format(date)
//
//
//        order.pickUp_Date = formatter3.format(date)
//        order.id = formatedDate
//        order.dining_Option = option
//        order.status = "Pending"
//        order.total_Price = getTotal()
//        order.order_Time = formatter2.format(date)
//        order.order_Date = formatter3.format(date)
//    }

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
                item.store_name, option,("$saveCurrentDateTime $i"), currentDate
            , currentTime, pickUpTime, currentDate, email))

            i += 1
        }
    }

//    fun initOrderFoodList() {
//        var i = 0
//        for (item in cartArrayList) {
//            val calForDate = Calendar.getInstance().time
//            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm:ss") //or use getDateInstance()
//            val saveCurrentDateTime = formatter.format(calForDate)
//
//            orderFood.add(Order_Food(item.food_name, item.each_price!!, order.status,
//                item.quantity, item.remark, item.canteen_name,
//                item.store_name, order.dining_Option, ("$saveCurrentDateTime $i")))
//
//            i += 1
//        }
//
//
////        hashMap.forEach{(key, value) ->
////            orderFood.add(Order_Food(value.food_name, value.each_price!!, order.status,
////                value.quantity, value.remark, value.canteen_name,
////                value.store_name, order.dining_Option))
////        }
//    }

    fun getTotalPrice() : Float {
        var total : Float = "0.00".toFloat()

        for (item in cartArrayList) {
            total += (item.each_price!! * item.quantity!!).toFloat()
        }

        return total
    }

    private fun getTotal() : Double {
        var total = 0.00

        hashMap.forEach{(key, value) ->
            total += (value.each_price !!* value.quantity!!)
        }

        return total
    }

    init {
        _activeButton.value = 0
    }

}
package com.example.fyp.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fyp.Class.Cart
import com.example.fyp.Class.Order
import com.example.fyp.Class.Order_Food

class CartViewModel : ViewModel() {
    private val _activeButton = MutableLiveData<Int>()
    val activeButton: LiveData<Int>
        get() = _activeButton

    var hashMap : HashMap<String, Cart> = HashMap()
    var order : Order = Order()
    var orderFood : ArrayList<Order_Food> = ArrayList()


    fun activateCartButton() {
        _activeButton.value = _activeButton.value?.plus(1)
    }

    fun deActivateCartButton() {
        _activeButton.value = _activeButton.value?.minus(1)
    }

    fun addItem(cart : Cart) {
        hashMap.put(cart.cart_ID!!, cart)
    }

    fun removeItem(cart : Cart) {
        hashMap.remove(cart.cart_ID)
    }

    fun removeAll() {
        hashMap = HashMap()
    }

    fun setOrderValue(pickTime : String, option : String) {
        order.pickUp_Time = pickTime
        order.pickUp_Date = "Date"
        order.id = "123456"
        order.dining_Option = option
        order.status = "Pending"
        order.total_Price = getTotal()
        order.order_Time = "OrderTime"
        order.order_Date = "OrderDate"
    }

    fun initOrderFoodList() {
        hashMap.forEach{(key, value) ->
            orderFood.add(Order_Food(value.food_name, value.each_price!!, order.status,
                value.quantity, value.remark, value.canteen_name,
                value.store_name, order.dining_Option))
        }
    }

    private fun getTotal() : Double {
        return 0.00
    }

    init {
        _activeButton.value = 0
    }

}
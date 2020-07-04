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

    fun setOrderValue( option : String) {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm") //or use getDateInstance()
        val formatter2 = SimpleDateFormat("HH:mm") //or use getDateInstance()
        val formatter3 = SimpleDateFormat("dd.MM.yyyy") //or use getDateInstance()
        val formatedDate = formatter.format(date)


        order.pickUp_Date = formatter3.format(date)
        order.id = formatedDate
        order.dining_Option = option
        order.status = "Pending"
        order.total_Price = getTotal()
        order.order_Time = formatter2.format(date)
        order.order_Date = formatter3.format(date)
    }

    fun setTime(time : String) {
        order.pickUp_Time = time
    }

    fun initOrderFoodList() {
        hashMap.forEach{(key, value) ->
            orderFood.add(Order_Food(value.food_name, value.each_price!!, order.status,
                value.quantity, value.remark, value.canteen_name,
                value.store_name, order.dining_Option))
        }
    }

    private fun getTotal() : Double {
        var total = 0.00

        hashMap.forEach{(key, value) ->
            total += (value.each_price !!* value.quantity!!)
        }

        return total
    }

    private fun createCanteenOrder() {
        var canteenStoreList = ArrayList<String>()

        hashMap.forEach{(key, value) ->
            val canteenStore = value.canteen_name + value.store_name

            if (!canteenStoreList.contains(canteenStore)) {
                createNewCanteenOrder(value)
            }
        }
    }

    private fun createNewCanteenOrder(cart : Cart) {

    }

    init {
        _activeButton.value = 0
    }

}
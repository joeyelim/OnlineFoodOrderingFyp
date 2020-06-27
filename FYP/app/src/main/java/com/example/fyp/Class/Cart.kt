package com.example.fyp.Class

class Cart {
    var food_name : String? = ""
    var each_price : Double? = 0.00
    var quantity : Int? = 0
    var remark : String? = ""
    var canteen_name : String? = ""
    var store_name : String? = ""
    var cart_ID : String? = ""

    constructor()

    constructor(
        food_name: String?,
        each_price: Double?,
        quantity: Int?,
        remark: String?,
        canteen_name: String?,
        store_name: String?,
        cart_ID: String?
    ) {
        this.food_name = food_name
        this.each_price = each_price
        this.quantity = quantity
        this.remark = remark
        this.canteen_name = canteen_name
        this.store_name = store_name
        this.cart_ID = cart_ID
    }


}
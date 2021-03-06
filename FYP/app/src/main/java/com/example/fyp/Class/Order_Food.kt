package com.example.fyp.Class

class Order_Food {
    var food_Name: String? = ""
    var each_Price: Double? = 0.0
    var status: String? = ""
    var quantity: Int? = 0
    var remark: String? = ""
    var canteen_Name: String? = ""
    var store_Name: String? = ""
    var dining_option : String? = ""
    var id : String? = ""
    var order_Date : String? = ""
    var order_Time : String? = ""
    var pickUp_Date : String? = ""
    var pickUp_Time : String? = ""
    var email : String? = ""
    var imageUrl : String? = ""


    constructor()

    constructor(
        food_Name: String?,
        each_Price: Double,
        status: String?,
        quantity: Int?,
        remark: String?,
        canteen_Name: String?,
        store_Name: String?,
        dining_option : String?,
        id : String?,
        order_Date : String?,
        order_Time : String? ,
        pickUp_Time : String? ,
        pickUp_Date : String?,
        email : String?,
        imageUrl : String?
    ) {
        this.food_Name = food_Name
        this.each_Price = each_Price
        this.quantity = quantity
        this.remark = remark
        this.canteen_Name = canteen_Name
        this.store_Name = store_Name
        this.status = status
        this.dining_option = dining_option
        this.id = id
        this.order_Date = order_Date
        this.order_Time = order_Time
        this.pickUp_Time = pickUp_Time
        this.pickUp_Date = pickUp_Date
        this.email = email
        this.imageUrl = imageUrl
    }
}
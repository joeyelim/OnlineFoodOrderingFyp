package com.example.fyp.Class

class Order_Food {
    var food_Name: String? = ""
    var each_Price: Double? = 0.0
    var status: String? = ""
    var quantity: Int? = 0
    var remark: String? = ""
    var canteen_Name: String? = ""
    var store_Name: String? = ""
    var option : String? = ""
    var id : String? = ""

    constructor()

    constructor(
        food_Name: String?,
        each_Price: Double,
        status: String?,
        quantity: Int?,
        remark: String?,
        canteen_Name: String?,
        store_Name: String?,
        option : String?,
        id : String?


    ) {
        this.food_Name = food_Name
        this.each_Price = each_Price
        this.quantity = quantity
        this.remark = remark
        this.canteen_Name = canteen_Name
        this.store_Name = store_Name
        this.status = status
        this.option = option
        this.id = id
    }
}
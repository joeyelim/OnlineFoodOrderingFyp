package com.example.fyp.Class

class Order {
    var PickUp_Date: String? = ""
    var Order_Date: String? = ""
    var Dining_Option: String? = ""
    var Progress: String? = ""
    var PickUp_Time: String? = ""
    var Order_Time: String? = ""
    var Total_Price: String? = ""

    constructor()

    constructor(
        pickUp_Date: String?,
        order_Date: String,
        dining_Option: String?,
        progress: String?,
        pickUp_Time: String?,
        order_Time: String?,
        total_Price: String?

    ) {
        this.Dining_Option = dining_Option
        this.Order_Date = order_Date
        this.PickUp_Date = pickUp_Date
        this.PickUp_Time = pickUp_Time
        this.Order_Time = order_Time
        this.Total_Price = total_Price
        this.Progress = progress
    }
}
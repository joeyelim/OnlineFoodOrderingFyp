package com.example.fyp.Class

class Order {
    var pickUp_Date: String? = ""
    var order_Date: String? = ""
    var dining_Option: String? = ""
    var status: String? = ""
    var pickUp_Time: String? = ""
    var order_Time: String? = ""
    var total_Price: String? = ""

    constructor()

    constructor(
        pickUp_Date: String?,
        order_Date: String,
        dining_Option: String?,
        status: String?,
        pickUp_Time: String?,
        order_Time: String?,
        total_Price: String?

    ) {
        this.dining_Option = dining_Option
        this.order_Date = order_Date
        this.pickUp_Date = pickUp_Date
        this.pickUp_Time = pickUp_Time
        this.order_Time = order_Time
        this.total_Price = total_Price
        this.status = status
    }
}
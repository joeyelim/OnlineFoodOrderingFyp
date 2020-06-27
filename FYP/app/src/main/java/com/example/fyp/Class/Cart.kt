package com.example.fyp.Class

class Cart {
    var Food_Name : String? = ""
    var Each_Price : Double? = 0.00
    var Quantity : Int? = 0
    var Remark : String? = ""
    var Canteen_Name : String? = ""
    var Store_Name : String? = ""
    var Cart_ID : String? = ""

    constructor()

    constructor(Food_Name: String?, Each_Price: Double?, Quantity : Int?, Remark : String?,
                Canteen_Name : String?, Store_Name : String?, Cart_ID : String?) {
        this.Food_Name = Food_Name
        this.Each_Price = Each_Price
        this.Quantity = Quantity
        this.Remark = Remark
        this.Canteen_Name = Canteen_Name
        this.Store_Name = Store_Name
        this.Cart_ID = Cart_ID
    }
}
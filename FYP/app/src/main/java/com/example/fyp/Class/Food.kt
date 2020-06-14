package com.example.fyp.Class

class Food {
    var food_name: String? = ""
    var food_image: String? = ""
    var price: Double? = null

    constructor(){

    }
    constructor(food_name: String?, food_image: String?, price : Double?) {
        this.food_name = food_name
        this.food_image = food_image
        this.price = price
    }


}
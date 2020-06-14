package com.example.fyp.Class

class Food {
    var food_name: String? = ""
    var food_image: String? = ""
    var small_price: Int? = null

    constructor(){

    }
    constructor(food_name: String?, food_image: String?, small_price: Int?) {
        this.food_name = food_name
        this.food_image = food_image
        this.small_price = small_price
    }


}
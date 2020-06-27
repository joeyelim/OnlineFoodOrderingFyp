package com.example.fyp.Class

class Food {
    var food_name: String? = ""
    var food_image: String? = ""
    var price: Double? = null
    var recipe_info : String? = ""
    var total_review : Int? = 0
    var total_star : Int? = 0
    var total_stock : Int? = 0
    var category : MutableList<String> = ArrayList()


    constructor()

    constructor(food_name: String?, food_image: String?, price : Double?, recipe_info : String?,
    total_star : Int?, total_review : Int?, total_stock : Int?, category : MutableList<String>
    ) {
        this.food_name = food_name
        this.food_image = food_image
        this.price = price
        this.category = category
        this.recipe_info = recipe_info
        this.total_review = total_review
        this.total_star = total_star
        this.total_stock = total_stock

    }


}
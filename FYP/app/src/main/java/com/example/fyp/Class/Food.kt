package com.example.fyp.Class

class Food {
    var food_name: String? = ""
    var food_image: String? = ""
    var small_price : Double? = null
    var large_price: Double? = null
    var recipe_info : String? = ""
    var total_review : Int? = 0
    var total_star : Int? = 0
    var total_stock : Int? = 0
    var category : MutableList<String> = ArrayList()


    constructor()
    constructor(
        food_name: String?,
        food_image: String?,
        small_price: Double?,
        large_price: Double?,
        recipe_info: String?,
        total_review: Int?,
        total_star: Int?,
        total_stock: Int?,
        category: MutableList<String>
    ) {
        this.food_name = food_name
        this.food_image = food_image
        this.small_price = small_price
        this.large_price = large_price
        this.recipe_info = recipe_info
        this.total_review = total_review
        this.total_star = total_star
        this.total_stock = total_stock
        this.category = category
    }


}
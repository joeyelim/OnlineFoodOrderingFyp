package com.example.fyp.Class

class Canteen{
    var canteen_name : String? = ""
    var image : String? = ""
    var time : String? = ""
    var type : String? = ""

    constructor()

    constructor(
        time: String?,
        image: String?,
        canteen_name : String?,
        type : String?

    ) {
        this.time = time
        this.image = image
        this.canteen_name = canteen_name
        this.type = type
    }
}
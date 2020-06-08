package com.example.fyp.Class

class Canteen{
    var canteenName : String? = ""
    var type : Int? = 0
    var image : String? = ""
    var time : String? = ""

    constructor()

    constructor(
        time: String?,
        image: String?,
        type: Int?,
        canteenName : String?

    ) {
        this.time = time
        this.image = image
        this.type = type
        this.canteenName = canteenName
    }
}
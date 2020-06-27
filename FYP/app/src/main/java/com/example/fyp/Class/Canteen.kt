package com.example.fyp.Class

class Canteen{
    var canteen_name : String? = ""
    var image : String? = ""
    var time : String? = ""
    var type : String? = ""

    constructor()
    constructor(canteen_name: String?, image: String?, time: String?, type: String?) {
        this.canteen_name = canteen_name
        this.image = image
        this.time = time
        this.type = type
    }


}
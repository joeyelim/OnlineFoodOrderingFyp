package com.example.fyp.Class

class CanteenStore {
    var store_name : String? = ""
    var store_image : String? = ""
    var canteen: String? = ""

    constructor()

    constructor(store_name: String?, store_image: String?, canteen: String?) {
        this.store_name = store_name
        this.store_image = store_image
        this.canteen = canteen
    }


}
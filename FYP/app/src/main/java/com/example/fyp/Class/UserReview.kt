package com.example.fyp.Class

class UserReview {
    var star : Float? = ("0.00").toFloat()
    var reviewTime : String? = ""
    var reviewDate : String? = ""
    var foodName : String? = ""
    var imageUrl : String? = ""
    var id : String? = ""

    constructor()
    constructor(star: Float?, reviewTime: String?, reviewDate: String?, foodName: String?,
                imageUrl : String?, id : String?) {
        this.star = star
        this.reviewTime = reviewTime
        this.reviewDate = reviewDate
        this.foodName = foodName
        this.imageUrl = imageUrl
        this.id = id
    }

}
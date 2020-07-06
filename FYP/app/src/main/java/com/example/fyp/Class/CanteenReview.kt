package com.example.fyp.Class

class CanteenReview {
    var reviewer : String? = ""
    var star : Float? = "0.00".toFloat()
    var review_time : String? = ""
    var review_date : String? = ""

    constructor()
    constructor(star: Float?, review_time: String?, review_date: String?, reviewer: String?
               ) {
        this.star = star
        this.review_time = review_time
        this.review_date = review_date
        this.reviewer = reviewer
    }
}
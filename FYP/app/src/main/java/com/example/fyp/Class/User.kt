package com.example.fyp.Class

class User {
    var profile_pic : String? = ""
    var first_name : String? = ""
    var last_name : String? = ""
    var phone_number: String? = ""
    var role : String? = ""
    var password : String? = ""
    var email : String? = ""

    constructor()

    constructor(
        profile_pic: String?,
        first_name: String?,
        last_name: String?,
        phone_number: String?,
        role: String?,
        password: String?,
        email: String?
    ) {
        this.profile_pic = profile_pic
        this.first_name = first_name
        this.last_name = last_name
        this.phone_number = phone_number
        this.role = role
        this.password = password
        this.email = email
    }
}
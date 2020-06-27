package com.example.fyp.Class

import java.util.*

class Notification {
    var notif_ID :String? = ""
    var sender : String? = ""
    var title : String? = ""
    var content : String? = ""
    var date : String? = ""
    var time : String? = ""
    var read : Boolean? = false

    constructor()

    constructor(
        notif_ID :String?,
        sender: String?,
        title: String?,
        content: String?,
        date: String?,
        time: String?,
        read: Boolean?
    ) {
        this.notif_ID = notif_ID
        this.sender = sender
        this.title = title
        this.content = content
        this.date = date
        this.time = time
        this.read = read
    }
}
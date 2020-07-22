package com.example.fyp.firebaseNotifications

import android.os.IBinder

data class NotificationData(
    val title:String,
    val message : String,
    val recipient: String
)
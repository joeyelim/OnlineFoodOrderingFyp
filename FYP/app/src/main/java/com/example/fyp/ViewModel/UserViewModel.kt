package com.example.fyp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.fyp.Class.Notification
import com.example.fyp.Class.User

class UserViewModel : ViewModel() {
    var user : User? = User()
    var notification : Notification = Notification()



}
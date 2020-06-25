package com.example.fyp.ViewModel

import androidx.lifecycle.ViewModel
import com.example.fyp.Class.Canteen
import com.example.fyp.Class.CanteenStore
import com.example.fyp.Class.Food

class CanteenViewModel : ViewModel() {
    var canteen : Canteen = Canteen()
    var store : CanteenStore = CanteenStore()
    var food : Food = Food()

    fun setCurrentCanteen(currentCanteen: Canteen) {
        canteen = currentCanteen
    }
}


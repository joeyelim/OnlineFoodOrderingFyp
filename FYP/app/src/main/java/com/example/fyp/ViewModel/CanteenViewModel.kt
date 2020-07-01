package com.example.fyp.ViewModel

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.fyp.Class.Canteen
import com.example.fyp.Class.CanteenStore
import com.example.fyp.Class.Food
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.fyp.Class.Notification
import java.text.DecimalFormat


class CanteenViewModel : ViewModel() {
    var canteen : Canteen = Canteen()
    var store : CanteenStore = CanteenStore()
    var food : Food = Food()


    fun setCurrentCanteen(currentCanteen: Canteen) {
        canteen = currentCanteen
    }

    fun setImage(view : ImageView, storage : StorageReference) {
        storage.downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .into(view)
        }
    }

    fun getPrice(price : Double) : String {
        val formatter = DecimalFormat("RM ###,###,##0.00")
        return formatter.format(price)
    }
}


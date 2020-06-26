package com.example.fyp.ViewModel

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.example.fyp.Class.Canteen
import com.example.fyp.Class.CanteenStore
import com.example.fyp.Class.Food
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

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
}


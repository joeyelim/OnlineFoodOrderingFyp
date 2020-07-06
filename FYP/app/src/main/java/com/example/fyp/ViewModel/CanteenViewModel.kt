package com.example.fyp.ViewModel

import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fyp.Class.Canteen
import com.example.fyp.Class.CanteenStore
import com.example.fyp.Class.Food
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.text.DecimalFormat


class CanteenViewModel : ViewModel() {
    var canteen: Canteen = Canteen()
    var store: CanteenStore = CanteenStore()
    var food: Food = Food()
    var userRating: Float? = "0.00".toFloat()
    var newRating: Float? = "0.00".toFloat()
    var gotReviewed: Boolean = false


    private val _rating = MutableLiveData<Float>()
    val rating: LiveData<Float>
        get() = _rating


    fun setRating(star: Float) {
        _rating.value = star
    }

    init {
        _rating.value = "0.00".toFloat()
    }

    fun setCurrentCanteen(currentCanteen: Canteen) {
        canteen = currentCanteen
    }

    fun getUserRating(currentUser: String) {
        val reviewID = canteen.canteen_name + store.store_name + food.food_name

        try {
            FirebaseFirestore.getInstance()
                .collection("User").document(currentUser)
                .collection("User_Review").document(reviewID)
                .get()
                .addOnSuccessListener {
                    if (it["star"] == null) {
                        userRating = "0.00".toFloat()
                        _rating.value = "0.00".toFloat()
                        gotReviewed = false

                    } else {
                        userRating = it["star"].toString().toFloat()
                        _rating.value = it["star"].toString().toFloat()
                        gotReviewed = true
                    }
                }
        } catch (e: Exception) {
            _rating.value = "0.00".toFloat()
            userRating = "0.00".toFloat()
        }

    }

    fun setImage(view: ImageView, storage: StorageReference) {
        storage.downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .into(view)
        }
    }

    fun getPrice(price: Double): String {
        val formatter = DecimalFormat("RM ###,###,##0.00")
        return formatter.format(price)
    }
}


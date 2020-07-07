package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Cart
import com.example.fyp.Class.Notification
import com.example.fyp.Class.UserReview
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.food_category_row.view.*
import kotlinx.android.synthetic.main.notification_row.view.*
import kotlinx.android.synthetic.main.place_order_food_row.view.*
import kotlinx.android.synthetic.main.user_rating_row.view.*
import java.text.DecimalFormat

//class UserProfileReviewRecycleView (options: FirestoreRecyclerOptions<UserReview>
//                                    , var context : Context
//) :
//    FirestoreRecyclerAdapter<UserReview, UserReviewViewHolder>(options) {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserReviewViewHolder {
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.user_rating_row, parent, false)
//
//        return UserReviewViewHolder(view, context)
//    }
//
//    override fun onBindViewHolder(holder: UserReviewViewHolder, position: Int, model: UserReview) {
//        holder.setCanteenState(model, holder)
//
//    }
//
//}
//
//class UserReviewViewHolder internal constructor(private val view: View, var context : Context) :
//    RecyclerView.ViewHolder(view) {
//
//    internal fun setCanteenState(review: UserReview, holder : UserReviewViewHolder) {
//        Log.i("Test", "Here")
//
//        holder.view.userProfileRating.rating = review.star!!.toFloat()
//
//        val image = view.findViewById<ImageView>(R.id.userProfileFood)
//        val a = FirebaseStorage.getInstance().getReference(review.imageUrl!!)
//
//        a.downloadUrl.addOnSuccessListener {
//            Picasso.get()
//                .load(it)
//                .into(image)
//        }
//    }
//}
class UserProfileReviewRecycleView : RecyclerView.Adapter<UserProfileReviewRecycleView.UserReviewViewHolder>() {

    var data = listOf<UserReview>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserReviewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_rating_row, parent, false)
        return UserReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(
        holder: UserReviewViewHolder,
        position: Int
    ) {
        holder.view.userProfileRating.rating = data[position].star!!.toFloat()
        holder.view.txtProfileFood.text = data[position].foodName

        val image = holder.view.findViewById<ImageView>(R.id.userProfileFood)
        val a = FirebaseStorage.getInstance().getReference(data[position].imageUrl!!)

        a.downloadUrl.addOnSuccessListener {
            Picasso.get()
                .load(it)
                .fit()
                .into(image)
        }
    }

    class UserReviewViewHolder(val view : View) : RecyclerView.ViewHolder(view)

}

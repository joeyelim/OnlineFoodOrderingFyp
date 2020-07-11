package com.example.fyp.FirestoreAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.UserReview
import com.example.fyp.Interface.OnRatingClick
import com.example.fyp.R
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_rating_row.view.*

class UserProfileReviewRecycleView (var itemClick : OnRatingClick) :
    RecyclerView.Adapter<UserProfileReviewRecycleView.UserReviewViewHolder>() {

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

        holder.view.cardOrderList.setOnClickListener {
            itemClick.OnRatingItemClick(data[position])
        }
    }

    class UserReviewViewHolder(val view: View) : RecyclerView.ViewHolder(view)

}

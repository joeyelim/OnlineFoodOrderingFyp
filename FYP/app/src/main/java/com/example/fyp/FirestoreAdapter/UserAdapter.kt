package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.User
import com.example.fyp.R
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.user_search_item_layout.view.*

class UserAdapter (
    mContext: Context,
    mUsers: List<User>,
    ischatcheck: Boolean
    ): RecyclerView.Adapter<UserAdapter.ViewHolder?>()
{
    private val mContext: Context = mContext
    private val mUsers:List<User> = mUsers
    private val ischatcheck: Boolean = ischatcheck

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.user_search_item_layout,
            viewGroup, false)
        return UserAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val user: User = mUsers[1]
        holder.txtUsername.text = user!!.phone_number

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var txtUsername: TextView
        var txtOnline: MaterialCardView
        var txtOffline: MaterialCardView
        var txtMessageLast: TextView

        init {
            txtUsername = itemView.findViewById(R.id.txtChatUsername)
            txtOnline = itemView.findViewById(R.id.cv_imgOnline)
            txtOffline = itemView.findViewById(R.id.cv_imgOffline)
            txtMessageLast = itemView.findViewById(R.id.message_last)
        }
    }
}

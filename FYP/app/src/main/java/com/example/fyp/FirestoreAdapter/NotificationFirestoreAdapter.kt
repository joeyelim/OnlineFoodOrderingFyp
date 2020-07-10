package com.example.fyp.FirestoreAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fyp.Class.Notification
import com.example.fyp.R
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.notification_row.view.*


class NotificationFirestoreAdapter (options: FirestoreRecyclerOptions<Notification>, var onListClick2: onListClick4
                                            , var context : Context
) :
    FirestoreRecyclerAdapter<Notification, NotificationViewHolder>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_row, parent, false)
        return NotificationViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int, model: Notification) {
        holder.setCanteenState(model, onListClick2, holder)

    }

}

class NotificationViewHolder internal constructor(private val view: View, var context : Context) :
    RecyclerView.ViewHolder(view) {

    internal fun setCanteenState(notif: Notification, onListClick2: onListClick4, holder : NotificationViewHolder) {

        holder.view.txtStore.text = notif.sender
        holder.view.txtNotifContent.text = notif.content
        holder.view.txtDate.text = notif.date
        holder.view.txtNotifTitle.text = notif.title



        holder.view.cvNotification.setOnClickListener {
            onListClick2.onItemClick(notif, adapterPosition)
        }

    }
}

interface onListClick4{
    fun onItemClick(notif: Notification, position: Int){

    }
}
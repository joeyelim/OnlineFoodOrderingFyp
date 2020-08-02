package com.example.fyp.Chat.Item

import android.content.Context
import com.example.fyp.Class.User
import com.example.fyp.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.user_search_item_layout.*

class PersonItem(val person: User,
                 val userId: String,
                 private val context: Context)
    : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.txtChatUsername.text = person.email
        viewHolder.message_last.text = ""

    }

    override fun getLayout() = R.layout.user_search_item_layout

}
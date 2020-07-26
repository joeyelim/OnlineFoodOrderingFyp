package com.example.fyp.Chat.Item

import android.content.Context
import com.example.fyp.Chat.model.TextMessage
import com.example.fyp.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder

class TextMessageItem(val message: TextMessage,
                      val context: Context)
    :Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout() = R.layout.item_text_msg

}
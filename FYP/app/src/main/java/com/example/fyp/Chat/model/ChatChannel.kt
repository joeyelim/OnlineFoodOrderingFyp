package com.example.fyp.Chat.model

data class ChatChannel(val userIds: MutableList<String>) {
    constructor(): this(mutableListOf())

}
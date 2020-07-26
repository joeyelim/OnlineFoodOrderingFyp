package com.example.fyp.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.fyp.R
import com.example.fyp.Util.FirestoreUtil
import com.google.firebase.firestore.ListenerRegistration
import com.xwray.groupie.kotlinandroidextensions.Item
import org.jetbrains.anko.toast
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.fragment.app.FragmentTransaction


class ChatActivity : AppCompatActivity() {
    private lateinit var messagesListenerRegistration: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.fyp.R.layout.activity_chat)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(AppConstant.USER_NAME)

        val otherUserId = intent.getStringExtra(AppConstant.USER_ID)
        FirestoreUtil.getOrCreateChatChannel(otherUserId) {channelId ->
            messagesListenerRegistration =
                FirestoreUtil.addChatMessageListener(channelId, this, this::onMessageChanged)
        }
    }

    private fun onMessageChanged(messages: List<Item>){
        toast("onMessageChangeRunning!")

    }

}

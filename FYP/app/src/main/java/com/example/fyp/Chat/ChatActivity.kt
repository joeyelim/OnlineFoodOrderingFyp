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
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fyp.Chat.model.MessageType
import com.example.fyp.Chat.model.TextMessage
import com.example.fyp.Class.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*


class ChatActivity : AppCompatActivity() {
    private lateinit var currentUser: User
    private lateinit var messagesListenerRegistration: ListenerRegistration
    private var shouldInitRecyclerView = true
    private lateinit var messagesSection: Section

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.fyp.R.layout.activity_chat)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = intent.getStringExtra(AppConstant.USER_NAME)

//        FirestoreUtil.getCurrentUser {
//            currentUser = it
//
//        }


        FirebaseFirestore.getInstance()
            .collection("User")
            .document(FirebaseAuth.getInstance().currentUser?.email!!)
            .get()
            .addOnSuccessListener {
                currentUser = it.toObject(User::class.java)
            }

        val otherUserId = intent.getStringExtra(AppConstant.USER_ID)
        FirestoreUtil.getOrCreateChatChannel(otherUserId) { channelId ->
            messagesListenerRegistration =
                FirestoreUtil.addChatMessageListener(channelId, this, this::updateRecyclerView)
            if(currentUser.role != "staff") {
                relativeLayout_message.visibility = View.GONE
            } else {
                relativeLayout_message.visibility = View.VISIBLE
                imageView_send.setOnClickListener {
                    val messageToSend =
                        TextMessage(
                            editText_message.text.toString(),
                            Calendar.getInstance().time,
                            FirebaseAuth.getInstance().currentUser?.email.toString(),
                            otherUserId,
                            currentUser.store.toString()
                        )
                    editText_message.setText("")
                    FirestoreUtil.sendMessage(messageToSend, channelId)
                }
                fab_send_image.setOnClickListener {
                    //TODO: Send image message

                }
            }
        }

    }



    private fun updateRecyclerView(message: List<Item>){
        fun init() {
            recycler_view_messages.apply {
                layoutManager = LinearLayoutManager(this@ChatActivity)
                adapter = GroupAdapter<ViewHolder>().apply{
                    messagesSection = Section(message)
                    this.add(messagesSection)
                }
                shouldInitRecyclerView = false
            }
        }
        fun updateItems() = messagesSection.update(message)

        if (shouldInitRecyclerView)
            init()
        else
            updateItems()

        recycler_view_messages.scrollToPosition(recycler_view_messages.adapter!!.itemCount - 1)

    }

}
